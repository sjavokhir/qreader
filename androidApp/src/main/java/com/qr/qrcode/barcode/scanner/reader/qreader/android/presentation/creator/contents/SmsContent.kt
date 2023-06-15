package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRTextField
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.sms.SmsContentEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.sms.SmsContentViewModel

@Composable
fun SmsContent(
    viewModel: SmsContentViewModel = viewModel(),
    onContent: (Boolean) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        onContent(state.isEnabled)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        QRTextField(
            value = state.phone,
            onValueChange = { viewModel.onEvent(SmsContentEvent.PhoneChanged(it)) },
            placeholder = stringResource(id = R.string.eg_phone_placeholder),
            hint = stringResource(id = R.string.phone_number),
            keyboardType = KeyboardType.Phone
        )

        QRTextField(
            modifier = Modifier.defaultMinSize(minHeight = 120.dp),
            value = state.message,
            onValueChange = { viewModel.onEvent(SmsContentEvent.MessageChanged(it)) },
            placeholder = stringResource(id = R.string.eg_message_placeholder),
            hint = stringResource(id = R.string.message),
        )
    }
}