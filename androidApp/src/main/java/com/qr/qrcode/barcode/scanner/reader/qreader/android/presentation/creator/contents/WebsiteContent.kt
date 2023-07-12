package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRTextField
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.website.WebsiteContentEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.website.WebsiteContentViewModel

@Composable
fun WebsiteContent(
    viewModel: WebsiteContentViewModel = viewModel(),
    encoded: String,
    onContent: (Boolean, String, String) -> Unit,
) {
    val strings = LocalStrings.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        onContent(state.isEnabled, state.encode(), state.decode())
    }

    LaunchedEffect(encoded) {
        viewModel.onEvent(WebsiteContentEvent.Encoded(encoded))
    }

    Column {
        QRTextField(
            value = state.website,
            onValueChange = {
                viewModel.onEvent(WebsiteContentEvent.WebsiteChanged(it))
            },
            placeholder = strings.egPlaceholderWebsite,
            hint = strings.website
        )
    }
}