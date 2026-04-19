package org.dsqrwym.homechat.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import homechat.composeapp.generated.resources.Res
import homechat.composeapp.generated.resources.message_me
import homechat.composeapp.generated.resources.message_system
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.dsqrwym.homechat.model.ChatMessage
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Instant

@Composable
fun MessageBubble(
    message: ChatMessage,
    isMe: Boolean,
    isSystem: Boolean = false,
    modifier: Modifier = Modifier,
) {
    when {
        isSystem -> SystemMessage(message)
        isMe -> MyMessage(message, modifier)
        else -> TheirMessage(message, modifier)
    }
}

@Composable
private fun SystemMessage(message: ChatMessage) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(Res.string.message_system) + ": ${message.text} ${formatterTime(message.timestamp)}",
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.small
                )
                .padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun TheirMessage(message: ChatMessage, modifier: Modifier = Modifier) {
    val bubbleColor = MaterialTheme.colorScheme.surfaceContainerHigh
    val textColor = MaterialTheme.colorScheme.onSurface

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
        val maxBubbleWidth = maxWidth * 0.5f
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
        ) {
            Avatar(
                label = message.username.take(1).uppercase(),
                modifier = Modifier.padding(top = 2.dp, end = 6.dp)
            )
            Column(
                modifier = Modifier
                    .widthIn(max = maxBubbleWidth)
                    .drawBehind {
                        drawChatBubble(
                            color = bubbleColor,
                            isMe = false,
                        )
                    }
                    .padding(start = 16.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
                horizontalAlignment = Alignment.End
            ) {
                Column {
                    SelectionContainer {
                        Text(
                            text = message.username,
                            fontWeight = FontWeight.SemiBold,
                            color = textColor.copy(alpha = 0.6f),
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }

                    SelectionContainer {
                        Text(
                            text = message.text,
                            color = textColor,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
                Timestamp(message.timestamp, textColor)
            }
        }
    }
}

@Composable
private fun MyMessage(message: ChatMessage, modifier: Modifier = Modifier) {
    val bubbleColor = MaterialTheme.colorScheme.primaryContainer
    val textColor = MaterialTheme.colorScheme.onPrimaryContainer

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
        val maxBubbleWidth = maxWidth * 0.5f
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top,
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = maxBubbleWidth)
                    .drawBehind {
                        drawChatBubble(
                            color = bubbleColor,
                            isMe = true,
                        )
                    }
                    .padding(start = 12.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                horizontalAlignment = Alignment.End
            ) {
                Column(modifier = Modifier) {
                    SelectionContainer {
                        Text(
                            text = message.text,
                            color = textColor,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
                Timestamp(message.timestamp, textColor)
            }
            Avatar(
                label = stringResource(Res.string.message_me).take(1),
                color = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier.padding(top = 2.dp, start = 6.dp)
            )
        }
    }
}

@Composable
private fun Avatar(
    label: String,
    color: Color = MaterialTheme.colorScheme.surfaceContainerHighest,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(color),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun Timestamp(epochMs: Long, baseColor: Color) {
    val formatted = remember(epochMs) {
        formatterTime(epochMs)
    }
    Text(
        text = formatted,
        style = MaterialTheme.typography.labelSmall,
        color = baseColor.copy(alpha = 0.45f),
        modifier = Modifier.padding(top = 2.dp),
    )
}

private fun formatterTime(epochMs: Long): String {
    val instant = Instant.fromEpochMilliseconds(epochMs)
    val dt = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val h = dt.hour.toString().padStart(2, '0')
    val m = dt.minute.toString().padStart(2, '0')
    return "$h:$m"
}

private fun DrawScope.drawChatBubble(
    color: Color,
    isMe: Boolean,
    cornerRadius: Dp = 14.dp,
    tailSize: Dp = 8.dp,       // 尖角底边
    tailHeight: Dp = 10.dp,    // 尖角伸出长度
) {
    val r = cornerRadius.toPx()
    val tw = tailSize.toPx()
    val th = tailHeight.toPx()
    val w = size.width
    val h = size.height
    val tailY = 20.dp.toPx()   // 尖角垂直位置（对齐头像中心）

    val rectLeft = if (isMe) 0f else th
    val rectRight = if (isMe) w - th else w

    val path = Path().apply {
        addRoundRect(RoundRect(rectLeft, 0f, rectRight, h, r, r))
        if (isMe) {
            // 右侧尖角
            moveTo(rectRight, tailY - tw / 2)
            lineTo(rectRight + th, tailY)
            lineTo(rectRight, tailY + tw / 2)
        } else {
            // 左侧尖角
            moveTo(rectLeft, tailY - tw / 2)
            lineTo(rectLeft - th, tailY)
            lineTo(rectLeft, tailY + tw / 2)
        }
        close()
    }
    drawPath(path, color)
}