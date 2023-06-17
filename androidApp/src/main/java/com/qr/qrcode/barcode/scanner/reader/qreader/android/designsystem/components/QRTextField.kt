package com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QRTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    hint: String? = null,
    leadingIcon: Painter? = null,
    trailingIcon: Painter? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    singleLine: Boolean = false,
    readOnly: Boolean = false
) {
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                isFocused = it.isFocused
            },
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onBackground
        ),
        readOnly = readOnly,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction,
        ),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.outline),
        decorationBox = { innerTextField ->
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (!hint.isNullOrEmpty()) {
                    Text(
                        text = hint,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (isFocused) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onBackground
                        }
                    )
                }

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .border(
                            width = if (isFocused) (1.5).dp else 1.dp,
                            color = if (isFocused) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                            },
                            shape = MaterialTheme.shapes.medium
                        )
                        .background(MaterialTheme.colorScheme.background)
                        .padding(
                            horizontal = 16.dp,
                            vertical = 14.dp
                        ),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    leadingIcon?.let {
                        QRIcon(
                            painter = it,
                            color = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(21.dp)
                        )
                    }

                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.TopStart
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.outline,
                                maxLines = if (singleLine) 1 else Int.MAX_VALUE,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        innerTextField()
                    }

                    trailingIcon?.let {
                        QRIcon(
                            painter = it,
                            color = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    )
}