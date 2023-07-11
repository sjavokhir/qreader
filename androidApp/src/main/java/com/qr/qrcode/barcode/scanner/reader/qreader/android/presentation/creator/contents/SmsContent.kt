package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRTextField
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.sms.SmsContentEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.sms.SmsContentViewModel

@Composable
fun SmsContent(
    viewModel: SmsContentViewModel = viewModel(),
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
            viewModel.onEvent(SmsContentEvent.Encoded(encoded))
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        QRTextField(
            value = state.phone,
            onValueChange = {
                viewModel.onEvent(SmsContentEvent.PhoneChanged(it))
            },
            placeholder = strings.egPlaceholderPhone,
            hint = strings.phoneNumber,
            keyboardType = KeyboardType.Phone
        )

        QRTextField(
            modifier = Modifier.defaultMinSize(minHeight = 120.dp),
            value = state.message,
            onValueChange = {
                viewModel.onEvent(SmsContentEvent.MessageChanged(it))
            },
            placeholder = strings.egPlaceholderSmsMessage,
            hint = strings.message,
        )
    }
}