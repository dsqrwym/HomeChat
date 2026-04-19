package org.dsqrwym.homechat.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Clock

const val SYSTEM_USER_ID = "system"
const val SYSTEM_USERNAME = "System"

@Serializable
data class ChatMessage(
    val userId: String,
    val username: String,
    val text: String,
    val timestamp: Long = Clock.System.now().toEpochMilliseconds(),
)

@Serializable
data class ChatSession(
    val userId: String,
    val username: String,
)

@Serializable
data class SendChatMessageRequest(
    val text: String,
)

@Serializable
sealed class ChatSocketEvent {
    @Serializable
    @SerialName("session")
    data class SessionAssigned(
        val session: ChatSession,
    ) : ChatSocketEvent()

    @Serializable
    @SerialName("message")
    data class MessageReceived(
        val message: ChatMessage,
    ) : ChatSocketEvent()

    @Serializable
    @SerialName("online_count")
    data class OnlineCountUpdated(val count: Int) : ChatSocketEvent()
}
