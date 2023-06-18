package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRTextField
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.business.BusinessContentEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.business.BusinessContentViewModel

@Composable
fun BusinessContent(
    viewModel: BusinessContentViewModel = viewModel(),
    onContent: (Boolean, String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        onContent(state.isEnabled, state.generateText)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        QRTextField(
            value = state.name,
            onValueChange = {
                viewModel.onEvent(BusinessContentEvent.NameChanged(it))
            },
            placeholder = stringResource(id = R.string.eg_placeholder_company_name),
            hint = stringResource(id = R.string.company_name)
        )

        QRTextField(
            value = state.industry,
            onValueChange = {
                viewModel.onEvent(BusinessContentEvent.IndustryChanged(it))
            },
            placeholder = stringResource(id = R.string.eg_placeholder_industry),
            hint = stringResource(id = R.string.industry)
        )

        QRTextField(
            value = state.phone,
            onValueChange = {
                viewModel.onEvent(BusinessContentEvent.PhoneChanged(it))
            },
            placeholder = stringResource(id = R.string.eg_placeholder_phone),
            hint = stringResource(id = R.string.phone_number),
            keyboardType = KeyboardType.Phone
        )

        QRTextField(
            value = state.email,
            onValueChange = {
                viewModel.onEvent(BusinessContentEvent.EmailChanged(it))
            },
            placeholder = stringResource(id = R.string.eg_placeholder_email),
            hint = stringResource(id = R.string.email_address),
            keyboardType = KeyboardType.Email
        )

        QRTextField(
            value = state.website,
            onValueChange = {
                viewModel.onEvent(BusinessContentEvent.WebsiteChanged(it))
            },
            placeholder = stringResource(id = R.string.eg_placeholder_website),
            hint = stringResource(id = R.string.website)
        )

        QRTextField(
            value = state.address,
            onValueChange = {
                viewModel.onEvent(BusinessContentEvent.AddressChanged(it))
            },
            placeholder = stringResource(id = R.string.eg_placeholder_address),
            hint = stringResource(id = R.string.address)
        )
    }
}