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

    private val currentState: EventContentState
        get() = state.value

    fun onEvent(event: EventContentEvent) {
        when (event) {
            is EventContentEvent.NameChanged -> onValueChanged(name = event.name)
            is EventContentEvent.LocationChanged -> onValueChanged(location = event.location)
            is EventContentEvent.DescriptionChanged -> onValueChanged(description = event.description)
            is EventContentEvent.ShowPicker -> onShowPicker(event.isStart)
            is EventContentEvent.DateTimeChanged -> onDateTimeChanged(event.timestamp)
        }
    }

    private fun onShowPicker(isStart: Boolean) {
        stateData.update { it.copy(isStart = isStart) }
    }

    private fun onDateTimeChanged(timestamp: Long) {
        stateData.update {
            if (currentState.isStart) {
                it.copy(
                    startTimestamp = timestamp,
                    startDateTime = timestamp.timestampToDateTime().defaultDateTime
                )
            } else {
                it.copy(
                    endTimestamp = timestamp,
                    endDateTime = timestamp.timestampToDateTime().defaultDateTime
                )
            }
        }
    }

    private fun onValueChanged(
        name: String? = null,
        location: String? = null,
        description: String? = null
    ) {
        stateData.update {
            val mName = name ?: it.name

            it.copy(
                name = mName,
                location = location ?: it.location,
                description = description ?: it.description,
                isEnabled = mName.isNotEmpty()
            )
        }
    }

//    fun getContent(): QrGenerateContent {
//        return QrGenerateContent(
//            qrContent = buildQrContent(), formattedContent = buildFormattedContent()
//        )
//    }

//    private fun buildQrContent(): String {
//        return buildString {
//            append("BEGIN:VEVENT\n")
//            append("SUMMARY:${currentState.name}\n")
//            append("DTSTART:${currentState.startDate.timestampToString(currentState.startTime)}\n")
//            append("DTEND:${currentState.endDate.timestampToString(currentState.endTime)}\n")
//            append("LOCATION:${currentState.location}\n")
//            append("DESCRIPTION:${currentState.description}\n")
//            append("END:VEVENT")
//        }
//    }
//
//    private fun buildFormattedContent(): String {
//        return buildString {
//            append("Event Name: ${currentState.name}\n")
//            append("Start Date and Time: ${currentState.startDateTime}\n")
//            append("End Date and Time: ${currentState.endDateTime}\n")
//            append("Event Location: ${currentState.location}\n")
//            append("Description: ${currentState.description}")
//        }
//    }
}