package org.dsqrwym.homechat.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import homechat.composeapp.generated.resources.Res
import homechat.composeapp.generated.resources.connection_join
import homechat.composeapp.generated.resources.connection_username_label
import homechat.composeapp.generated.resources.connection_username_placeholder
import org.jetbrains.compose.resources.stringResource

@Composable
fun ConnectionBar(
    username: String,
    isConnected: Boolean,
    onUsernameChange: (String) -> Unit,
    onConnect: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            modifier = Modifier.semantics {
                contentType = ContentType.Username
            },
            value = username,
            onValueChange = onUsernameChange,
            label = { Text(stringResource(Res.string.connection_username_label)) },
            placeholder = { Text(stringResource(Res.string.connection_username_placeholder)) },
            singleLine = true,
            enabled = !isConnected,
            keyboardActions = KeyboardActions(onAny = {
                onConnect()
                focusManager.moveFocus(FocusDirection.Down)
            }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Text),
        )
        FilledTonalButton(
            onClick = onConnect,
            enabled = !isConnected && username.isNotBlank(),
        ) {
            Text(stringResource(Res.string.connection_join))
        }
    }
}
