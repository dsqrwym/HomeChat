package org.dsqrwym.homechat.data.chat

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.dsqrwym.homechat.model.ChatMessage
import org.dsqrwym.homechat.model.ChatSession

class ChatRepository(
    private val chatApi: ChatApi,
) {
    val messages: Flow<ChatMessage> = chatApi.messages
    val session: Flow<ChatSession> = chatApi.session
    val isConnected: StateFlow<Boolean> = chatApi.isConnected

    private val pendingMessages = mutableListOf<String>()

    suspend fun connect(username: String) {
        chatApi.connect(username)
        pendingMessages.toList().forEach { chatApi.send(it) }
        pendingMessages.clear()
    }

    suspend fun send(text: String) {
        if (!isConnected.value) {
            pendingMessages.add(text)
            return
        }

        try {
            chatApi.send(text)
        } catch (_: Exception) {
            pendingMessages.add(text)
        }
    }

    suspend fun disconnect() {
        chatApi.disconnect()
    }
}
