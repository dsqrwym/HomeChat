package org.dsqrwym.homechat.routes

import io.ktor.server.routing.Route
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.serialization.json.Json
import org.dsqrwym.homechat.model.SendChatMessageRequest
import org.dsqrwym.homechat.services.ChatService

private val chatJson = Json { ignoreUnknownKeys = true }

fun Route.chatRoutes(chatService: ChatService) {
    webSocket("/chat") {
        var userId = ""

        try {
            val session = chatService.addConnection(call.request.queryParameters["username"], this)
            userId = session.userId

            for (frame in incoming) {
                when (frame) {
                    is Frame.Text -> {
                        val senderId = userId
                        val receivedText = frame.readText()
                        if (chatService.isRateLimited(senderId)) {
                            close(
                                CloseReason(
                                    code = CloseReason.Codes.VIOLATED_POLICY,
                                    message = "Too many messages, please try again later",
                                ),
                            )
                            break
                        }

                        val payload = runCatching {
                            chatJson.decodeFromString<SendChatMessageRequest>(receivedText)
                        }.getOrNull()

                        if (payload == null) {
                            close(
                                CloseReason(
                                    code = CloseReason.Codes.CANNOT_ACCEPT,
                                    message = "Invalid message payload",
                                ),
                            )
                            break
                        }

                        chatService.processAndForwardMessage(senderId, payload.text)
                    }

                    else -> Unit
                }
            }
        } catch (e: Exception) {
            println("WebSocket error: ${e.message}")
        } finally {
            if (userId.isNotBlank()) {
                chatService.removeConnection(userId)
            }
        }
    }
}
