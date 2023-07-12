package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QROutlinedButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRTextField
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.LocationPickerScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.location.LocationContentEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.location.LocationContentViewModel
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.spec.Direction

@Composable
fun LocationContent(
    viewModel: LocationContentViewModel = viewModel(),
    encoded: String,
    onContent: (Boolean, String, String) -> Unit,
    onNavigate: (Direction) -> Unit,
    resultLocation: ResultRecipient<LocationPickerScreenDestination, String>
) {
    val strings = LocalStrings.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    resultLocation.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {}
            is NavResult.Value -> {
                viewModel.onEvent(LocationContentEvent.LocationChanged(result.value))
            }
        }
    }

    LaunchedEffect(state) {
        onContent(state.isEnabled, state.encode(), state.decode())
    }

    LaunchedEffect(encoded) {
        viewModel.onEvent(LocationContentEvent.Encoded(encoded))
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        QRTextField(
            value = state.latitude,
            onValueChange = {
                viewModel.onEvent(LocationContentEvent.LatitudeChanged(it))
            },
            placeholder = strings.egPlaceholderLatitude,
            hint = strings.latitude,
            keyboardType = KeyboardType.Decimal
        )

        QRTextField(
            value = state.longitude,
            onValueChange = {
                viewModel.onEvent(LocationContentEvent.LongitudeChanged(it))
            },
            placeholder = strings.egPlaceholderLongitude,
            hint = strings.longitude,
            keyboardType = KeyboardType.Decimal
        )

        QROutlinedButton(
            text = strings.selectLocation,
            onClick = {
                onNavigate(LocationPickerScreenDestination)
            },
            leadingIcon = painterResource(id = R.drawable.ic_select_location)
        )
    }
}