package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.event

import com.qr.qrcode.barcode.scanner.reader.qreader.core.datetime.timestampToDateTime
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EventContentViewModel : KMMViewModel() {

    private val stateData = MutableStateFlow(viewModelScope, EventContentState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    fun onEvent(event: EventContentEvent) {
        when (event) {
            is EventContentEvent.Encoded -> onEncoded(event.value)
            is EventContentEvent.NameChanged -> onValueChanged(name = event.name)
            is EventContentEvent.LocationChanged -> onValueChanged(location = event.location)
            is EventContentEvent.DescriptionChanged -> onValueChanged(description = event.description)
            is EventContentEvent.AllDayChecked -> onValueChanged(isAllDay = event.isChecked)
            is EventContentEvent.ShowPicker -> onShowPicker(event.isStart)
            is EventContentEvent.DateTimeChanged -> onDateTimeChanged(event.timestamp)
        }
    }

    private fun onEncoded(value: String) {
        val content = value.toEventContent() ?: return

        onValueChanged(
            name = content.name,
            location = content.location,
            description = content.description,
        )
    }

    private fun onShowPicker(isStart: Boolean) {
        stateData.update { it.copy(isStart = isStart) }
    }

    private fun onDateTimeChanged(timestamp: Long) {
        stateData.update {
            if (state.value.isStart) {
                it.copy(
                    startTimestamp = timestamp,
                    startDateTime = if (it.isAllDay) {
                        timestamp.timestampToDateTime().defaultDate
                    } else {
                        timestamp.timestampToDateTime().defaultDateTime
                    }
                )
            } else {
                it.copy(
                    endTimestamp = timestamp,
                    endDateTime = if (it.isAllDay) {
                        timestamp.timestampToDateTime().defaultDate
                    } else {
                        timestamp.timestampToDateTime().defaultDateTime
                    }
                )
            }
        }
    }

    private fun onValueChanged(
        name: String? = null,
        location: String? = null,
        description: String? = null,
        isAllDay: Boolean? = null
    ) {
        stateData.update {
            val mName = name ?: it.name

            it.copy(
                name = mName,
                location = location ?: it.location,
                description = description ?: it.description,
                isAllDay = isAllDay ?: it.isAllDay,
                isEnabled = mName.isNotEmpty()
            )
        }
    }
}