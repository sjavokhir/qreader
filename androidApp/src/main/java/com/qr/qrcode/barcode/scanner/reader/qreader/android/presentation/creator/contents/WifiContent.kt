package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRDropdown
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRTextField
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.wifi.WifiContentEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.wifi.WifiContentViewModel

@Composable
fun WifiContent(
    viewModel: WifiContentViewModel = viewModel(),
    onContent: (Boolean) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(state) {
        onContent(state.isEnabled)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        QRTextField(
            value = state.networkName,
            onValueChange = {
                viewModel.onEvent(WifiContentEvent.NetworkNameChanged(it))
            },
            placeholder = stringResource(id = R.string.eg_placeholder_wifi_name),
            hint = stringResource(id = R.string.network_name)
        )

        QRTextField(
            value = state.password,
            onValueChange = {
                viewModel.onEvent(WifiContentEvent.PasswordChanged(it))
            },
            placeholder = stringResource(id = R.string.eg_placeholder_wifi_password),
            hint = stringResource(id = R.string.password),
        )

        QRDropdown(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            hint = stringResource(id = R.string.encryption_type),
            selectedOption = state.selectedType,
            onSelectedOption = {
                viewModel.onEvent(WifiContentEvent.SelectType(it))
                expanded = false
            },
            options = state.encryptionTypes
        )
    }
}