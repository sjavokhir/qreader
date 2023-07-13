package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.event

import com.qr.qrcode.barcode.scanner.reader.qreader.core.datetime.toDefaultDate
import com.qr.qrcode.barcode.scanner.reader.qreader.core.datetime.toDefaultDateTime
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
        if (state.value.isSetEncoded) return

        val content = value.toEventContent() ?: return

        onValueChanged(
            name = content.name,
            location = content.location,
            description = content.description,
            isAllDay = content.isAllDay,
            startTimestamp = content.startTimestamp,
            endTimestamp = content.endTimestamp,
        )
    }

    private fun onShowPicker(isStart: Boolean) {
        stateData.update { it.copy(isStart = isStart) }
    }

    private fun onDateTimeChanged(timestamp: Long) {
        if (state.value.isStart) {
            onValueChanged(startTimestamp = timestamp)
        } else {
            onValueChanged(endTimestamp = timestamp)
        }
    }

    private fun onValueChanged(
        name: String? = null,
        location: String? = null,
        description: String? = null,
        isAllDay: Boolean? = null,
        startTimestamp: Long? = null,
        endTimestamp: Long? = null,
    ) {
        stateData.update {
            val mName = name ?: it.name
            val mIsAllDay = isAllDay ?: it.isAllDay
            val mStartTimestamp = startTimestamp ?: it.startTimestamp
            val mEndTimestamp = endTimestamp ?: it.endTimestamp

            it.copy(
                name = mName,
                location = location ?: it.location,
                description = description ?: it.description,
                isAllDay = mIsAllDay,
                startTimestamp = mStartTimestamp,
                startDateTime = if (mStartTimestamp == 0L) {
                    ""
                } else if (mIsAllDay) {
                    mStartTimestamp.toDefaultDate()
                } else {
                    mStartTimestamp.toDefaultDateTime()
                },
                endTimestamp = mEndTimestamp,
                endDateTime = if (mEndTimestamp == 0L) {
                    ""
                } else if (mIsAllDay) {
                    mEndTimestamp.toDefaultDate()
                } else {
                    mEndTimestamp.toDefaultDateTime()
                },
                isEnabled = mName.isNotEmpty() && mStartTimestamp != 0L,
                isSetEncoded = true
            )
        }
    }
}