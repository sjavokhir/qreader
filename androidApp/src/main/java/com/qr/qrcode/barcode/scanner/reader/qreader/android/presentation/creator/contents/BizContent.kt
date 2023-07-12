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
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.biz.BizContentEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.biz.BizContentViewModel

@Composable
fun BizContent(
    viewModel: BizContentViewModel = viewModel(),
    encoded: String,
    onContent: (Boolean, String, String) -> Unit
) {
    val strings = LocalStrings.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        onContent(state.isEnabled, state.encode(), state.decode())
    }

    LaunchedEffect(encoded) {
        viewModel.onEvent(BizContentEvent.Encoded(encoded))
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        QRTextField(
            value = state.firstName,
            onValueChange = {
                viewModel.onEvent(BizContentEvent.FirstNameChanged(it))
            },
            placeholder = strings.egPlaceholderFirstName,
            hint = strings.name
        )

        QRTextField(
            value = state.lastName,
            onValueChange = {
                viewModel.onEvent(BizContentEvent.LastNameChanged(it))
            },
            placeholder = strings.egPlaceholderLastName,
            hint = strings.lastName
        )

        QRTextField(
            value = state.company,
            onValueChange = {
                viewModel.onEvent(BizContentEvent.CompanyChanged(it))
            },
            placeholder = strings.egPlaceholderCompanyName,
            hint = strings.companyName
        )

        QRTextField(
            value = state.job,
            onValueChange = {
                viewModel.onEvent(BizContentEvent.JobChanged(it))
            },
            placeholder = strings.egPlaceholderJob,
            hint = strings.job
        )

        QRTextField(
            value = state.phone,
            onValueChange = {
                viewModel.onEvent(BizContentEvent.PhoneChanged(it))
            },
            placeholder = strings.egPlaceholderPhone,
            hint = strings.phoneNumber,
            keyboardType = KeyboardType.Phone
        )

        QRTextField(
            value = state.email,
            onValueChange = {
                viewModel.onEvent(BizContentEvent.EmailChanged(it))
            },
            placeholder = strings.egPlaceholderEmail,
            hint = strings.emailAddress,
            keyboardType = KeyboardType.Email
        )

        QRTextField(
            value = state.website,
            onValueChange = {
                viewModel.onEvent(BizContentEvent.WebsiteChanged(it))
            },
            placeholder = strings.egPlaceholderWebsite,
            hint = strings.website
        )

        QRTextField(
            value = state.address,
            onValueChange = {
                viewModel.onEvent(BizContentEvent.AddressChanged(it))
            },
            placeholder = strings.egPlaceholderAddress,
            hint = strings.address
        )
    }
}