package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRTextField
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.email.EmailContentEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.email.EmailContentViewModel

@Composable
fun EmailContent(
    viewModel: EmailContentViewModel = viewModel(),
    onContent: (Boolean, String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        onContent(state.isEnabled, state.generateText)
    }

    Column {
        QRTextField(
            value = state.email,
            onValueChange = {
                viewModel.onEvent(EmailContentEvent.EmailChanged(it))
            },
            placeholder = stringResource(id = R.string.eg_placeholder_email),
            hint = stringResource(id = R.string.email_address)
        )
    }
}