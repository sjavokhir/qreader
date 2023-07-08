package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.event

import com.qr.qrcode.barcode.scanner.reader.qreader.core.datetime.timestampToString
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.QrData

data class EventContentState(
    val name: String = "",
    val location: String = "",
    val description: String = "",
    val isAllDay: Boolean = false,
    val isStart: Boolean = true,
    val startTimestamp: Long = 0L,
    val startDateTime: String = "",
    val endTimestamp: Long = 0L,
    val endDateTime: String = "",
    val isEnabled: Boolean = false
) : QrData {

    override fun encode(): String = buildString {
        append("BEGIN:VEVENT\n")
        append("SUMMARY:$name\n")

        if (isAllDay) {
            append("DTSTART;VALUE=DATE:${startTimestamp.timestampToString(isAllDay)}\n")
        } else {
            append("DTSTART:${startTimestamp.timestampToString(isAllDay)}\n")
        }

        if (isAllDay) {
            append("DTEND;VALUE=DATE:${endTimestamp.timestampToString(isAllDay)}\n")
        } else {
            append("DTEND:${endTimestamp.timestampToString(isAllDay)}\n")
        }

        append("LOCATION:$location\n")
        append("DESCRIPTION:$description\n")
        append("END:VEVENT")
    }
}