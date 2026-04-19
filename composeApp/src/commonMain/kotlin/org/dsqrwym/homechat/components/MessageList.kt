package org.dsqrwym.homechat.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import homechat.composeapp.generated.resources.Res
import homechat.composeapp.generated.resources.message_list_empty_hint
import kotlinx.coroutines.flow.distinctUntilChanged
import org.dsqrwym.homechat.model.ChatMessage
import org.dsqrwym.homechat.model.SYSTEM_USER_ID
import org.jetbrains.compose.resources.stringResource

@Composable
fun MessageList(
    messages: List<ChatMessage>,
    currentUserId: String?,
    modifier: Modifier = Modifier,
) {
    if (messages.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(12.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(Res.string.message_list_empty_hint),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        return
    }

    val listState = rememberLazyListState()
    var shouldAutoScroll by remember { mutableStateOf(true) }

    LaunchedEffect(listState) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
            lastVisibleIndex >= layoutInfo.totalItemsCount - 3
        }
            .distinctUntilChanged()
            .collect { shouldAutoScroll = it }
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            val lastMessage = messages.last()
            val isLastMessageFromMe = lastMessage.userId == currentUserId
            // 如果是自己发送的消息，总是滚动；否则遵循 shouldAutoScroll
            if (isLastMessageFromMe || shouldAutoScroll) {
                listState.animateScrollToItem(messages.lastIndex)
            }
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(vertical = 2.dp),
    ) {
        itemsIndexed(
            items = messages,
            key = { index, message -> "${message.userId}-${message.timestamp}-$index" },
        ) { _, message ->
            val isMe = message.userId == currentUserId
            val isSystem = message.userId == SYSTEM_USER_ID

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = when {
                    isSystem -> Arrangement.Center
                    isMe -> Arrangement.End
                    else -> Arrangement.Start
                },
            ) {
                MessageBubble(
                    message = message,
                    isMe = isMe,
                    isSystem = isSystem,
                )
            }
        }
    }
}
