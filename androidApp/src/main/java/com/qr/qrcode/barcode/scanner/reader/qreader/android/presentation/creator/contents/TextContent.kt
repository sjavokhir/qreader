package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRTextField
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.text.TextContentEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.text.TextContentViewModel

@Composable
fun TextContent(
    viewModel: TextContentViewModel = viewModel(),
    encoded: String,
    onContent: (Boolean, String, String) -> Unit,
) {
    val strings = LocalStrings.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        onContent(state.isEnabled, state.encode(), state.decode())
    }

    LaunchedEffect(encoded) {
        if (!state.isSetEncoded) {
            viewModel.onEvent(TextContentEvent.Encoded(encoded))
        }
    }

    Column {
        QRTextField(
            modifier = Modifier.defaultMinSize(minHeight = 120.dp),
            value = state.text,
            onValueChange = {
                viewModel.onEvent(TextContentEvent.TextChanged(it))
            },
            placeholder = strings.egPlaceholderText,
            hint = strings.text,
        )
    }
}