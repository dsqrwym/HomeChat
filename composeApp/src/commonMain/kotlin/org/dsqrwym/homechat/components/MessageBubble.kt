package org.dsqrwym.homechat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import homechat.composeapp.generated.resources.Res
import homechat.composeapp.generated.resources.message_me
import homechat.composeapp.generated.resources.message_system
import org.jetbrains.compose.resources.stringResource
import org.dsqrwym.homechat.model.ChatMessage

@Composable
fun MessageBubble(
    message: ChatMessage,
    isMe: Boolean,
    isSystem: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val bubbleColor = when {
        isSystem -> MaterialTheme.colorScheme.surfaceVariant
        isMe -> MaterialTheme.colorScheme.primaryContainer
        else -> MaterialTheme.colorScheme.surfaceContainerHigh
    }
    val textColor = when {
        isSystem -> MaterialTheme.colorScheme.onSurfaceVariant
        isMe -> MaterialTheme.colorScheme.onPrimaryContainer
        else -> MaterialTheme.colorScheme.onSurface
    }
    val maxWidth: Dp = if (isSystem) 320.dp else 420.dp
    SelectionContainer {
        Column(
            modifier = modifier
                .widthIn(max = maxWidth)
                .background(color = bubbleColor, shape = RoundedCornerShape(14.dp))
                .padding(horizontal = 12.dp, vertical = 10.dp),
        ) {
            if (isSystem) {
                Text(
                    text = stringResource(Res.string.message_system),
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    style = MaterialTheme.typography.labelLarge,
                )
            } else {
                Text(
                    text = if (isMe) stringResource(Res.string.message_me) else message.username,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
            Text(
                text = message.text,
                color = textColor,
                style = if (isSystem) MaterialTheme.typography.bodyMedium else MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = if (isSystem) 0.dp else 2.dp),
            )
        }
    }
}
