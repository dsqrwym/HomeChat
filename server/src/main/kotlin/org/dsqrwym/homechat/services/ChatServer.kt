package org.dsqrwym.homechat.services

import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.json.Json
import org.dsqrwym.homechat.model.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

class ChatService {
    private data class Connection(
        val userId: String,
        val username: String,
        val session: DefaultWebSocketServerSession,
    )

    private val connections = ConcurrentHashMap<String, Connection>()
    private val rates = ConcurrentHashMap<String, UserRateState>()
    private val onlineUsers = AtomicInteger(0)
    private val usernameLock = Any()
    private val json = Json { classDiscriminator = "type" }

    private data class UserRateState(
        var windowStartMillis: Long,
        var count: Int,
    )

    companion object {
        private const val USERNAME_MAX_LENGTH = 24
        private const val MESSAGE_MAX_LENGTH = 1000
        private const val RATE_LIMIT_WINDOW_MILLIS = 1_000L
        private const val RATE_LIMIT_MAX_MESSAGES = 8
    }

    fun normalizeUsername(raw: String?): String {
        val candidate = raw?.trim().orEmpty()
        val fallback = "Guest_${System.currentTimeMillis() % 100000}"
        val normalized = if (candidate.isBlank()) fallback else candidate.take(USERNAME_MAX_LENGTH)
        return normalized
            .filter { it.isLetterOrDigit() || it == '_' || it == '-' || it == ' ' }
            .trim()
            .ifBlank { fallback }
    }

    private fun ensureUniqueUsername(baseUsername: String): String {
        synchronized(usernameLock) {
            val usedNames = connections.values.map { it.username }.toHashSet()
            if (baseUsername !in usedNames) return baseUsername

            var suffix = 2
            while (true) {
                val candidate = "$baseUsername $suffix".take(USERNAME_MAX_LENGTH)
                if (candidate !in usedNames) return candidate
                suffix += 1
            }
        }
    }

    private fun normalizeMessage(raw: String): String {
        return raw.replace("\u0000", "").trim().take(MESSAGE_MAX_LENGTH)
    }

    fun validateAndNormalizeMessage(raw: String): String? {
        val normalized = normalizeMessage(raw)
        return normalized.takeIf { it.isNotBlank() }
    }

    fun isRateLimited(userId: String, nowMillis: Long = System.currentTimeMillis()): Boolean {
        val state = rates.computeIfAbsent(userId) { UserRateState(nowMillis, 0) }
        synchronized(state) {
            if (nowMillis - state.windowStartMillis >= RATE_LIMIT_WINDOW_MILLIS) {
                state.windowStartMillis = nowMillis
                state.count = 1
                return false
            }

            state.count += 1
            return state.count > RATE_LIMIT_MAX_MESSAGES
        }
    }

    suspend fun addConnection(rawUsername: String?, session: DefaultWebSocketServerSession): ChatSession {
        val assignedUsername = ensureUniqueUsername(normalizeUsername(rawUsername))
        val connection = Connection(
            userId = UUID.randomUUID().toString(),
            username = assignedUsername,
            session = session,
        )
        connections[connection.userId] = connection
        onlineUsers.incrementAndGet()

        val chatSession = ChatSession(
            userId = connection.userId,
            username = connection.username,
        )
        session.send(
            Frame.Text(
                json.encodeToString(
                    ChatSocketEvent.serializer(),
                    ChatSocketEvent.SessionAssigned(chatSession),
                ),
            ),
        )
        broadcastMessage(
            ChatMessage(
                userId = SYSTEM_USER_ID,
                username = SYSTEM_USERNAME,
                text = "${connection.username} joined the chat",
            ),
        )
        broadcastOnlineCount()
        return chatSession
    }

    suspend fun removeConnection(userId: String) {
        val removed = connections.remove(userId)
        rates.remove(userId)
        if (removed != null) {
            onlineUsers.decrementAndGet()
            broadcastMessage(
                ChatMessage(
                    userId = SYSTEM_USER_ID,
                    username = SYSTEM_USERNAME,
                    text = "${removed.username} left the chat",
                ),
            )
            broadcastOnlineCount()
        }
    }

    private suspend fun broadcastMessage(message: ChatMessage) {
        val payload = json.encodeToString(
            ChatSocketEvent.serializer(),
            ChatSocketEvent.MessageReceived(message),
        )
        val staleUsers = mutableListOf<String>()
        connections.values.forEach { connection ->
            try {
                connection.session.send(Frame.Text(payload))
            } catch (_: Exception) {
                staleUsers += connection.userId
            }
        }

        if (staleUsers.isNotEmpty()) {
            staleUsers.forEach {
                if (connections.remove(it) != null) {
                    rates.remove(it)
                    onlineUsers.decrementAndGet()
                }
            }
        }
    }

    private suspend fun broadcastOnlineCount() {
        val payload = json.encodeToString(
            ChatSocketEvent.serializer(),
            ChatSocketEvent.OnlineCountUpdated(onlineUsers.get()),
        )
        connections.values.forEach { connection ->
            try { connection.session.send(Frame.Text(payload)) }
            catch (_: Exception) { }
        }
    }

    suspend fun processAndForwardMessage(senderId: String, messageText: String) {
        val connection = connections[senderId] ?: return
        val normalizedMessage = validateAndNormalizeMessage(messageText) ?: return
        val chatMessage = ChatMessage(
            userId = connection.userId,
            username = connection.username,
            text = normalizedMessage,
        )
        broadcastMessage(chatMessage)
    }

    suspend fun processExternalMessage(username: String, messageText: String) {
        val normalizedMessage = validateAndNormalizeMessage(messageText) ?: return
        broadcastMessage(
            ChatMessage(
                userId = "external:$username",
                username = ensureUniqueUsername(normalizeUsername(username)),
                text = normalizedMessage,
            ),
        )
    }
}
