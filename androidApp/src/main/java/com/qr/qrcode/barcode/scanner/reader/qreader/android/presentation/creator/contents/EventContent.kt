package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.clickableSingle
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRTextField
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.DateTimePickerScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.event.EventContentEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.event.EventContentViewModel
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.spec.Direction

@Composable
fun EventContent(
    viewModel: EventContentViewModel = viewModel(),
    onContent: (Boolean, String) -> Unit,
    onNavigate: (Direction) -> Unit,
    resultTimestamp: ResultRecipient<DateTimePickerScreenDestination, Long>
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    resultTimestamp.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {}
            is NavResult.Value -> {
                viewModel.onEvent(EventContentEvent.DateTimeChanged(result.value))
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
            value = state.name,
            onValueChange = {
                viewModel.onEvent(EventContentEvent.NameChanged(it))
            },
            placeholder = stringResource(id = R.string.eg_placeholder_event_name),
            hint = stringResource(id = R.string.event_name)
        )

        QRTextField(
            value = state.startDateTime,
            onValueChange = {},
            placeholder = stringResource(id = R.string.eg_placeholder_date_time),
            hint = stringResource(id = R.string.start_date_time),
            trailingIcon = painterResource(id = R.drawable.ic_today),
            readOnly = true,
            modifier = Modifier.clickableSingle(
                onClick = {
                    viewModel.onEvent(EventContentEvent.ShowPicker(true))
                    onNavigate(DateTimePickerScreenDestination)
                },
                hasIndication = false
            )
        )

        QRTextField(
            value = state.endDateTime,
            onValueChange = {},
            placeholder = stringResource(id = R.string.eg_placeholder_date_time),
            hint = stringResource(id = R.string.end_date_time),
            trailingIcon = painterResource(id = R.drawable.ic_today),
            readOnly = true,
            modifier = Modifier.clickableSingle(
                onClick = {
                    viewModel.onEvent(EventContentEvent.ShowPicker(false))
                    onNavigate(DateTimePickerScreenDestination)
                },
                hasIndication = false
            )
        )

        QRTextField(
            value = state.location,
            onValueChange = {
                viewModel.onEvent(EventContentEvent.LocationChanged(it))
            },
            placeholder = stringResource(id = R.string.eg_placeholder_location),
            hint = stringResource(id = R.string.location)
        )

        QRTextField(
            modifier = Modifier.defaultMinSize(minHeight = 120.dp),
            value = state.description,
            onValueChange = {
                viewModel.onEvent(EventContentEvent.DescriptionChanged(it))
            },
            placeholder = stringResource(id = R.string.eg_placeholder_event_description),
            hint = stringResource(id = R.string.description)
        )
    }
}