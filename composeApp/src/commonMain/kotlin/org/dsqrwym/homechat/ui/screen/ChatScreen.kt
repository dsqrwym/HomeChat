package org.dsqrwym.homechat.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import org.dsqrwym.homechat.ui.components.ChatTopBar
import org.dsqrwym.homechat.ui.components.ConnectionBar
import org.dsqrwym.homechat.ui.components.MessageInputBar
import org.dsqrwym.homechat.ui.components.MessageList
import org.dsqrwym.homechat.ui.viewmodel.ChatViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {
    val viewModel = koinViewModel<ChatViewModel>()
    val uiState = viewModel.uiState
    val username = uiState.username
    val isConnected = uiState.isConnected || uiState.isConnecting
    val count by viewModel.onlineCount.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ChatTopBar(
                username = username,
                isConnected = isConnected,
                scrollBehavior = scrollBehavior,
                onlineCount = count
            )
        },
        bottomBar = {
            BottomAppBar {
                MessageInputBar(
                    input = uiState.input,
                    enabled = uiState.isConnected,
                    onInputChange = viewModel::onInputChange,
                    onSend = viewModel::send,
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(!isConnected, modifier = Modifier.wrapContentSize().padding(8.dp)) {
                ConnectionBar(
                    username = username,
                    isConnected = isConnected,
                    onUsernameChange = viewModel::onUsernameChange,
                    onConnect = viewModel::connect,
                )
            }

            MessageList(
                messages = uiState.messages,
                currentUserId = uiState.currentUserId,
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 10.dp, bottom = 8.dp),
            )
        }
    }
}
