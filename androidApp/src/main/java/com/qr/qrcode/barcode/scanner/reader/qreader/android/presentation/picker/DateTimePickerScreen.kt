package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.picker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.DividerContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRFilledButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.core.datetime.actualDateMillis
import com.qr.qrcode.barcode.scanner.reader.qreader.core.datetime.currentTimestamp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun DateTimePickerScreen(
    resultNavigator: ResultBackNavigator<Long>
) {
    val strings = LocalStrings.current

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = currentTimestamp(),
        initialDisplayMode = DisplayMode.Input
    )
    val timePickerState = rememberTimePickerState(
        initialHour = 9,
        is24Hour = true
    )

    QRBackground {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn {
                item {
                    DatePicker(
                        state = datePickerState,
                        title = {
                            Text(
                                text = strings.selectDate,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(
                                    PaddingValues(
                                        start = 24.dp,
                                        end = 24.dp,
                                        top = 20.dp
                                    )
                                )
                            )
                        }
                    )
                }
                item {
                    Text(
                        text = strings.selectTime,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(
                            horizontal = 24.dp,
                            vertical = 20.dp
                        )
                    )
                }
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        TimePicker(state = timePickerState)
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(72.dp))
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .align(Alignment.BottomCenter),
            ) {
                DividerContent()

                QRFilledButton(
                    text = strings.select,
                    onClick = {
                        val actualDateMillis = datePickerState.selectedDateMillis.actualDateMillis(
                            hour = timePickerState.hour,
                            minute = timePickerState.minute
                        )
                        resultNavigator.navigateBack(actualDateMillis)
                    },
                    modifier = Modifier.padding(
                        horizontal = 20.dp,
                        vertical = 16.dp
                    )
                )
            }
        }

    }
}