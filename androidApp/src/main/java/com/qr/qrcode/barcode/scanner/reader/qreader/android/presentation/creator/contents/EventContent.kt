package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.clickableSingle
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRIcon
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRTextField
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.localization.LocalStrings
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
    val strings = LocalStrings.current

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
        onContent(state.isEnabled, state.encode())
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        QRTextField(
            value = state.name,
            onValueChange = {
                viewModel.onEvent(EventContentEvent.NameChanged(it))
            },
            placeholder = strings.egPlaceholderEventName,
            hint = strings.eventName
        )

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = strings.allDayEvent,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.weight(1f))

            Switch(
                checked = state.isAllDay,
                onCheckedChange = {
                    viewModel.onEvent(EventContentEvent.AllDayChecked(it))
                },
                modifier = Modifier
                    .width(39.dp)
                    .height(24.dp)
                    .scale(.85f)
            )
        }

        QRTextField(
            value = state.startDateTime,
            onValueChange = {},
            placeholder = if (state.isAllDay) {
                strings.egPlaceholderDate
            } else {
                strings.egPlaceholderDateTime
            },
            hint = if (state.isAllDay) {
                strings.startDate
            } else {
                strings.startDateTime
            },
            trailingIcon = {
                QRIcon(
                    painter = painterResource(id = R.drawable.ic_today),
                    color = MaterialTheme.colorScheme.outline
                )
            },
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
            placeholder = if (state.isAllDay) {
                strings.egPlaceholderDate
            } else {
                strings.egPlaceholderDateTime
            },
            hint = if (state.isAllDay) {
                strings.endDate
            } else {
                strings.endDateTime
            },
            trailingIcon = {
                QRIcon(
                    painter = painterResource(id = R.drawable.ic_today),
                    color = MaterialTheme.colorScheme.outline
                )
            },
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
            placeholder = strings.egPlaceholderLocation,
            hint = strings.location
        )

        QRTextField(
            modifier = Modifier.defaultMinSize(minHeight = 120.dp),
            value = state.description,
            onValueChange = {
                viewModel.onEvent(EventContentEvent.DescriptionChanged(it))
            },
            placeholder = strings.egPlaceholderEventDescription,
            hint = strings.description
        )
    }
}