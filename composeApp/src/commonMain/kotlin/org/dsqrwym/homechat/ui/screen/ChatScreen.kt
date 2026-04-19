package org.dsqrwym.homechat.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.dsqrwym.homechat.components.ChatTopBar
import org.dsqrwym.homechat.components.ConnectionBar
import org.dsqrwym.homechat.components.MessageInputBar
import org.dsqrwym.homechat.components.MessageList
import org.dsqrwym.homechat.ui.viewmodel.ChatViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatScreen() {
    val viewModel = koinViewModel<ChatViewModel>()
    val uiState = viewModel.uiState

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .safeContentPadding()
            .fillMaxSize()
            .padding(12.dp),
    ) {
        ChatTopBar(username = uiState.username, isConnected = uiState.isConnected)

        ConnectionBar(
            username = uiState.username,
            isConnected = uiState.isConnected || uiState.isConnecting,
            onUsernameChange = viewModel::onUsernameChange,
            onConnect = viewModel::connect,
        )

        MessageList(
            messages = uiState.messages,
            currentUserId = uiState.currentUserId,
            modifier = Modifier
                .weight(1f)
                .padding(top = 10.dp, bottom = 8.dp),
        )

        MessageInputBar(
            input = uiState.input,
            enabled = uiState.isConnected,
            onInputChange = viewModel::onInputChange,
            onSend = viewModel::send,
        )
    }
}
