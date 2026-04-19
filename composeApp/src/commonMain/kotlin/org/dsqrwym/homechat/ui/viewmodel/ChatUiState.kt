package org.dsqrwym.homechat.ui.viewmodel

import org.dsqrwym.homechat.model.ChatMessage

data class ChatUiState(
    val currentUserId: String? = null,
    val username: String = "",
    val input: String = "",
    val isConnecting: Boolean = false,
    val isConnected: Boolean = false,
    val messages: List<ChatMessage> = emptyList(),
)
