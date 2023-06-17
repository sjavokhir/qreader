package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.event

data class EventContentState(
    val name: String = "",
    val location: String = "",
    val description: String = "",
    val isEnabled: Boolean = false,
    val isStart: Boolean = true,
    val startTimestamp: Long = 0L,
    val startDateTime: String = "",
    val endTimestamp: Long = 0L,
    val endDateTime: String = ""
)