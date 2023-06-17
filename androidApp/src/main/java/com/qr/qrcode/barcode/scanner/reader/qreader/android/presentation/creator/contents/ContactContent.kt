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
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.contact.ContactContentEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.contact.ContactContentViewModel

@Composable
fun ContactContent(
    viewModel: ContactContentViewModel = viewModel(),
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
            value = state.firstName,
            onValueChange = {
                viewModel.onEvent(ContactContentEvent.FirstNameChanged(it))
            },
            placeholder = stringResource(id = R.string.eg_placeholder_first_name),
            hint = stringResource(id = R.string.first_name)
        )

        QRTextField(
            value = state.lastName,
            onValueChange = {
                viewModel.onEvent(ContactContentEvent.LastNameChanged(it))
            },
            placeholder = stringResource(id = R.string.eg_placeholder_last_name),
            hint = stringResource(id = R.string.last_name)
        )

        QRTextField(
            value = state.phone,
            onValueChange = {
                viewModel.onEvent(ContactContentEvent.PhoneChanged(it))
            },
            placeholder = stringResource(id = R.string.eg_placeholder_phone),
            hint = stringResource(id = R.string.phone_number),
            keyboardType = KeyboardType.Phone
        )

        QRTextField(
            value = state.email,
            onValueChange = {
                viewModel.onEvent(ContactContentEvent.EmailChanged(it))
            },
            placeholder = stringResource(id = R.string.eg_placeholder_email),
            hint = stringResource(id = R.string.email_address),
            keyboardType = KeyboardType.Email
        )

        QRTextField(
            value = state.website,
            onValueChange = {
                viewModel.onEvent(ContactContentEvent.WebsiteChanged(it))
            },
            placeholder = stringResource(id = R.string.eg_placeholder_website),
            hint = stringResource(id = R.string.website)
        )

        QRTextField(
            value = state.address,
            onValueChange = {
                viewModel.onEvent(ContactContentEvent.AddressChanged(it))
            },
            placeholder = stringResource(id = R.string.eg_placeholder_address),
            hint = stringResource(id = R.string.address)
        )
    }
}