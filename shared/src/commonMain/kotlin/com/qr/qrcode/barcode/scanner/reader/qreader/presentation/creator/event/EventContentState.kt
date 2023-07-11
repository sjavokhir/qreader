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
    val isEnabled: Boolean = false,
    val isSetEncoded: Boolean = false
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

    override fun decode(): String = buildString {
        append(name)

        if (startDateTime.isNotEmpty()) {
            append("\n").append(startDateTime)
        }

        if (endDateTime.isNotEmpty()) {
            append("\n").append(endDateTime)
        }

        if (location.isNotEmpty()) {
            append("\n").append(location)
        }

        if (description.isNotEmpty()) {
            append("\n").append(description)
        }
    }
}

fun String.toEventContent(): EventContentState? {
    return try {
        val nameMatch = Regex("SUMMARY:(.*?)\\n").find(this)
        val locationMatch = Regex("LOCATION:(.*?)\\n").find(this)
        val descriptionMatch = Regex("DESCRIPTION:(.*?)\\n").find(this)

        val name = nameMatch?.groupValues?.get(1)
        val location = locationMatch?.groupValues?.get(1)
        val description = descriptionMatch?.groupValues?.get(1)

        EventContentState(
            name = name.orEmpty(),
            location = location.orEmpty(),
            description = description.orEmpty()
        )
    } catch (_: Throwable) {
        null
    }
}