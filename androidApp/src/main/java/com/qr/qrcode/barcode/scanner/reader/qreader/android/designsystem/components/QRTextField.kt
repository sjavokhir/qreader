package com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun QRTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    placeholder: String? = null,
    trailingIcon: Painter? = null,
    trailingIconClick: (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    singleLine: Boolean = false,
    readOnly: Boolean = false,
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.bodyLarge,
        label = { Text(text = hint) },
        placeholder = {
            placeholder?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        },
        trailingIcon = {
            trailingIcon?.let {
                IconButton(onClick = { trailingIconClick?.invoke() }) {
                    QRIcon(
                        painter = it,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction,
        ),
        singleLine = singleLine,
        readOnly = readOnly,
        shape = MaterialTheme.shapes.medium
    )
}