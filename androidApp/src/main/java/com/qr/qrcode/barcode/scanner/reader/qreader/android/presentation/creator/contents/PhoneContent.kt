package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRTextField
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.phone.PhoneContentEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.phone.PhoneContentViewModel

@Composable
fun PhoneContent(
    viewModel: PhoneContentViewModel = viewModel(),
    onContent: (Boolean, String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        onContent(state.isEnabled, state.generateText)
    }

    Column {
        QRTextField(
            value = state.phone,
            onValueChange = {
                viewModel.onEvent(PhoneContentEvent.PhoneChanged(it))
            },
            placeholder = stringResource(id = R.string.eg_placeholder_phone),
            hint = stringResource(id = R.string.phone_number),
            keyboardType = KeyboardType.Phone
        )
    }
}