package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QROutlinedButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRTextField
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.LocationPickerScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.event.EventContentEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.location.LocationContentEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.location.LocationContentViewModel
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.spec.Direction

@Composable
fun LocationContent(
    viewModel: LocationContentViewModel = viewModel(),
    onContent: (Boolean, String) -> Unit,
    onNavigate: (Direction) -> Unit,
    resultLocation: ResultRecipient<LocationPickerScreenDestination, String>
) {
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
        onContent(state.isEnabled, state.generateText)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        QRTextField(
            value = state.latitude?.toString().orEmpty(),
            onValueChange = {
                viewModel.onEvent(LocationContentEvent.LatitudeChanged(it))
            },
            placeholder = stringResource(id = R.string.eg_placeholder_latitude),
            hint = stringResource(id = R.string.latitude)
        )

        QRTextField(
            value = state.longitude?.toString().orEmpty(),
            onValueChange = {
                viewModel.onEvent(LocationContentEvent.LongitudeChanged(it))
            },
            placeholder = stringResource(id = R.string.eg_placeholder_longitude),
            hint = stringResource(id = R.string.longitude)
        )

        QROutlinedButton(
            text = stringResource(id = R.string.action_select_location),
            onClick = {
                onNavigate(LocationPickerScreenDestination)
            },
            leadingIcon = painterResource(id = R.drawable.ic_select_location)
        )
    }
}