package org.dsqrwym.homechat.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.dsqrwym.homechat.data.chat.ChatRepository
import org.dsqrwym.homechat.model.ChatMessage
import org.dsqrwym.homechat.model.SYSTEM_USERNAME
import org.dsqrwym.homechat.model.SYSTEM_USER_ID
import kotlin.time.Clock

class ChatViewModel(
    private val repository: ChatRepository,
) : ViewModel() {
    var uiState by mutableStateOf(ChatUiState())
        private set
    val onlineCount = repository.onlineCount
    private var connectJob: Job? = null

    init {
        viewModelScope.launch {
            repository.isConnected.collect { connected ->
                uiState = uiState.copy(
                    isConnected = connected,
                    isConnecting = false,
                )
            }
        }

        viewModelScope.launch {
            repository.messages.collect { message ->
                uiState = uiState.copy(messages = uiState.messages + message)
            }
        }

        viewModelScope.launch {
            repository.session.collect { session ->
                uiState = uiState.copy(
                    currentUserId = session.userId,
                    username = session.username,
                )
            }
        }
    }

    fun onUsernameChange(value: String) {
        uiState = uiState.copy(username = value)
    }

    fun onInputChange(value: String) {
        uiState = uiState.copy(input = value)
    }

    fun connect() {
        val normalizedUsername = uiState.username.trim()
        if (normalizedUsername.isBlank() || uiState.isConnected || uiState.isConnecting) return

        connectJob?.cancel()
        connectJob = viewModelScope.launch {
            uiState = uiState.copy(
                username = normalizedUsername,
                isConnecting = true,
            )
            runCatching {
                repository.connect(normalizedUsername)
            }.onFailure { error ->
                uiState = uiState.copy(
                    currentUserId = null,
                    isConnected = false,
                    isConnecting = false,
                    messages = uiState.messages + ChatMessage(
                        userId = SYSTEM_USER_ID,
                        username = SYSTEM_USERNAME,
                        text = "连接失败: ${error.message}",
                        timestamp = Clock.System.now().toEpochMilliseconds(),
                    ),
                )
            }
        }
    }

    fun send() {
        val content = uiState.input.trim()
        if (!uiState.isConnected || content.isBlank()) return

        viewModelScope.launch {
            repository.send(content)
            uiState = uiState.copy(input = "")
        }
    }

    override fun onCleared() {
        super.onCleared()
        connectJob?.cancel()
        viewModelScope.launch {
            repository.disconnect()
        }
    }
}
