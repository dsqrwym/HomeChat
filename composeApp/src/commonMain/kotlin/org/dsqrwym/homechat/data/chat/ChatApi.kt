package org.dsqrwym.homechat.data.chat

import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.json.Json
import org.dsqrwym.homechat.model.*
import org.dsqrwym.homechat.network.ApiConfig
import kotlin.time.Clock

class ChatApi(
    private val httpClient: HttpClient,
    private val json: Json = Json {
        ignoreUnknownKeys = true
        classDiscriminator = "type"
    },
) {
    private val _messages = MutableSharedFlow<ChatMessage>(extraBufferCapacity = 64)
    val messages: Flow<ChatMessage> = _messages.asSharedFlow()

    private val _session = MutableSharedFlow<ChatSession>(extraBufferCapacity = 1)
    val session: Flow<ChatSession> = _session.asSharedFlow()

    private val _onlineCount = MutableStateFlow(0)
    val onlineCount: StateFlow<Int> = _onlineCount.asStateFlow()

    private val _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var socketSession: DefaultClientWebSocketSession? = null
    private var receiverJob: Job? = null

    suspend fun connect(username: String) {
        check(!_isConnected.value) { "Already connected, call disconnect() first" }
        receiverJob?.cancel()

        socketSession = httpClient.webSocketSession {
            url {
                val isSecure = ApiConfig.BASE_URL.startsWith("https")
                protocol = if (isSecure) URLProtocol.WSS else URLProtocol.WS
                host = ApiConfig.SERVER_HOST
                port = ApiConfig.SERVER_PORT
                path(ApiConfig.WS_PATH)
                parameters.append("username", username)
            }
        }.also { createdSession ->
            startReceiving(createdSession)
        }
    }

    suspend fun send(text: String) {
        val payload = json.encodeToString(
            SendChatMessageRequest.serializer(),
            SendChatMessageRequest(text = text),
        )
        socketSession?.send(Frame.Text(payload))
            ?: throw IllegalStateException("Not connected")
    }

    suspend fun disconnect() {
        _isConnected.value = false
        receiverJob?.cancel()
        receiverJob = null
        socketSession?.close()
        socketSession = null
    }

    private fun startReceiving(activeSession: DefaultClientWebSocketSession) {
        receiverJob = scope.launch {
            try {
                for (frame in activeSession.incoming) {
                    if (frame is Frame.Text) {
                        when (val event = json.decodeFromString<ChatSocketEvent>(frame.readText())) {
                            is ChatSocketEvent.MessageReceived -> _messages.emit(event.message)
                            is ChatSocketEvent.SessionAssigned -> {
                                _isConnected.value = true
                                _session.emit(event.session)
                            }
                            is ChatSocketEvent.OnlineCountUpdated -> _onlineCount.value = event.count
                        }
                    }
                }
            } catch (e: Exception) {
                if (_isConnected.value) {
                    _messages.emit(
                        ChatMessage(
                            userId = SYSTEM_USER_ID,
                            username = SYSTEM_USERNAME,
                            text = "连接已断开: ${e.message}",
                            timestamp = Clock.System.now().toEpochMilliseconds(),
                        ),
                    )
                }
            } finally {
                _isConnected.value = false
                if (socketSession === activeSession) {
                    socketSession = null
                }
                activeSession.close()
            }
        }
    }
}
