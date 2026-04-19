package org.dsqrwym.homechat.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import homechat.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun MessageInputBar(
    input: String,
    enabled: Boolean,
    onInputChange: (String) -> Unit,
    onSend: () -> Unit,
) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = onInputChange,
                label = { Text(stringResource(Res.string.message_input_label)) },
                placeholder = {
                    Text(
                        if (enabled) {
                            stringResource(Res.string.message_input_placeholder_enabled)
                        } else {
                            stringResource(Res.string.message_input_placeholder_disabled)
                        },
                    )
                },
                singleLine = true,
                enabled = enabled,
                modifier = Modifier.weight(1f),
                keyboardActions = KeyboardActions(onAny = { onSend() }),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Text),
            )
            FilledTonalButton(
                onClick = onSend,
                enabled = enabled && input.isNotBlank(),
            ) {
                Text(stringResource(Res.string.message_send))
            }
        }
    }
}
