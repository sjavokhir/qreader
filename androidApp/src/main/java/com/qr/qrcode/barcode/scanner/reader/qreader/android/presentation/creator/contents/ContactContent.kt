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
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.contact.ContactContentEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.contact.ContactContentViewModel

@Composable
fun ContactContent(
    viewModel: ContactContentViewModel = viewModel(),
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
            viewModel.onEvent(ContactContentEvent.Encoded(encoded))
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        QRTextField(
            value = state.name,
            onValueChange = {
                viewModel.onEvent(ContactContentEvent.NameChanged(it))
            },
            placeholder = strings.egPlaceholderFirstName,
            hint = strings.name
        )

        QRTextField(
            value = state.phone,
            onValueChange = {
                viewModel.onEvent(ContactContentEvent.PhoneChanged(it))
            },
            placeholder = strings.egPlaceholderPhone,
            hint = strings.phoneNumber,
            keyboardType = KeyboardType.Phone
        )

        QRTextField(
            value = state.email,
            onValueChange = {
                viewModel.onEvent(ContactContentEvent.EmailChanged(it))
            },
            placeholder = strings.egPlaceholderEmail,
            hint = strings.emailAddress,
            keyboardType = KeyboardType.Email
        )

        QRTextField(
            value = state.address,
            onValueChange = {
                viewModel.onEvent(ContactContentEvent.AddressChanged(it))
            },
            placeholder = strings.egPlaceholderAddress,
            hint = strings.address
        )
    }
}