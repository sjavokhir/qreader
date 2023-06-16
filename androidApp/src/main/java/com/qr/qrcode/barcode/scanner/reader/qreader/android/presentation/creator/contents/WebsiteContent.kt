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
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.website.WebsiteContentEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.website.WebsiteContentViewModel

@Composable
fun WebsiteContent(
    viewModel: WebsiteContentViewModel = viewModel(),
    onContent: (Boolean) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        onContent(state.isEnabled)
    }

    Column {
        QRTextField(
            value = state.website,
            onValueChange = {
                viewModel.onEvent(WebsiteContentEvent.WebsiteChanged(it))
            },
            placeholder = stringResource(id = R.string.eg_placeholder_website),
            hint = stringResource(id = R.string.website)
        )
    }
}