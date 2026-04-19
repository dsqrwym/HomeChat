package org.dsqrwym.homechat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import homechat.composeapp.generated.resources.Res
import homechat.composeapp.generated.resources.chat_status_offline
import homechat.composeapp.generated.resources.chat_status_online
import homechat.composeapp.generated.resources.chat_topbar_current_user
import homechat.composeapp.generated.resources.chat_topbar_input_username_first
import homechat.composeapp.generated.resources.chat_topbar_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun ChatTopBar(
    username: String,
    isConnected: Boolean,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(14.dp),
            )
            .padding(horizontal = 14.dp, vertical = 12.dp),
    ) {
        Text(
            text = stringResource(Res.string.chat_topbar_title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = if (username.isBlank()) {
                    stringResource(Res.string.chat_topbar_input_username_first)
                } else {
                    stringResource(Res.string.chat_topbar_current_user, username)
                },
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = if (isConnected) {
                    stringResource(Res.string.chat_status_online)
                } else {
                    stringResource(Res.string.chat_status_offline)
                },
                color = if (isConnected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}
