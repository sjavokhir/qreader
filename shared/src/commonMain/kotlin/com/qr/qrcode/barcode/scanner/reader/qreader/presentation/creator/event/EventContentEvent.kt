package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.event

sealed interface EventContentEvent {
    data class NameChanged(val name: String) : EventContentEvent
    data class LocationChanged(val location: String) : EventContentEvent
    data class DescriptionChanged(val description: String) : EventContentEvent
    data class AllDayChecked(val isChecked: Boolean) : EventContentEvent
    data class ShowPicker(val isStart: Boolean) : EventContentEvent
    data class DateTimeChanged(val timestamp: Long) : EventContentEvent
}
