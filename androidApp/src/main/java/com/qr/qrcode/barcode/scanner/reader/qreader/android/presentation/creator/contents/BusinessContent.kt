package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRTextField
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.business.BusinessContentEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.business.BusinessContentViewModel

@Composable
fun BusinessContent(
    viewModel: BusinessContentViewModel = viewModel(),
    encoded: String,
    onContent: (Boolean, String, String) -> Unit
) {
    val strings = LocalStrings.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        onContent(state.isEnabled, state.encode(), state.decode())
    }

    LaunchedEffect(encoded) {
        if (!state.isSetEncoded) {
            viewModel.onEvent(BusinessContentEvent.Encoded(encoded))
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        QRTextField(
            value = state.name,
            onValueChange = {
                viewModel.onEvent(BusinessContentEvent.NameChanged(it))
            },
            placeholder = strings.egPlaceholderCompanyName,
            hint = strings.companyName
        )

        QRTextField(
            value = state.industry,
            onValueChange = {
                viewModel.onEvent(BusinessContentEvent.IndustryChanged(it))
            },
            placeholder = strings.egPlaceholderIndustry,
            hint = strings.industry
        )

        QRTextField(
            value = state.phone,
            onValueChange = {
                viewModel.onEvent(BusinessContentEvent.PhoneChanged(it))
            },
            placeholder = strings.egPlaceholderPhone,
            hint = strings.phoneNumber,
            keyboardType = KeyboardType.Phone
        )

        QRTextField(
            value = state.email,
            onValueChange = {
                viewModel.onEvent(BusinessContentEvent.EmailChanged(it))
            },
            placeholder = strings.egPlaceholderEmail,
            hint = strings.emailAddress,
            keyboardType = KeyboardType.Email
        )

        QRTextField(
            value = state.website,
            onValueChange = {
                viewModel.onEvent(BusinessContentEvent.WebsiteChanged(it))
            },
            placeholder = strings.egPlaceholderWebsite,
            hint = strings.website
        )

        QRTextField(
            value = state.address,
            onValueChange = {
                viewModel.onEvent(BusinessContentEvent.AddressChanged(it))
            },
            placeholder = strings.egPlaceholderAddress,
            hint = strings.address
        )
    }
}