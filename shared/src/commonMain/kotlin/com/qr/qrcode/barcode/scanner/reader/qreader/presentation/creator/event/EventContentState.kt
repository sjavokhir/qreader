package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.event

import com.qr.qrcode.barcode.scanner.reader.qreader.core.datetime.timestampToDT
import com.qr.qrcode.barcode.scanner.reader.qreader.core.datetime.toTimestamp
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
            append("DTSTART;VALUE=DATE:${startTimestamp.timestampToDT(isAllDay)}\n")
        } else {
            append("DTSTART:${startTimestamp.timestampToDT(isAllDay)}\n")
        }

        if (isAllDay) {
            append("DTEND;VALUE=DATE:${endTimestamp.timestampToDT(isAllDay)}\n")
        } else {
            append("DTEND:${endTimestamp.timestampToDT(isAllDay)}\n")
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

    override fun isNotBlank(): Boolean {
        return "$name$location$description$startDateTime$endDateTime".isNotEmpty()
    }
}

fun String.toEventContent(): EventContentState? {
    return try {
        val nameMatch = Regex("SUMMARY:(.*?)\\n").find(this)
        val locationMatch = Regex("LOCATION:(.*?)\\n").find(this)
        val descriptionMatch = Regex("DESCRIPTION:(.*?)\\n").find(this)
        val startDateTimeMatch = Regex("DTSTART:(.*?)\\n").find(this)
        val startDateMatch = Regex("DTSTART;VALUE=DATE:(.*?)\\n").find(this)
        val endDateTimeMatch = Regex("DTEND:(.*?)\\n").find(this)
        val endDateMatch = Regex("DTEND;VALUE=DATE:(.*?)\\n").find(this)

        val name = nameMatch?.groupValues?.get(1)
        val location = locationMatch?.groupValues?.get(1)
        val description = descriptionMatch?.groupValues?.get(1)
        val startDateTime = startDateTimeMatch?.groupValues?.get(1)
        val startDate = startDateMatch?.groupValues?.get(1)
        val endDateTime = endDateTimeMatch?.groupValues?.get(1)
        val endDate = endDateMatch?.groupValues?.get(1)

        val startTimestamp = if (!startDateTime.isNullOrEmpty()) {
            startDateTime.toTimestamp(false)
        } else if (!startDate.isNullOrEmpty()) {
            startDate.toTimestamp(true)
        } else 0L

        val endTimestamp = if (!endDateTime.isNullOrEmpty()) {
            endDateTime.toTimestamp(false)
        } else if (!endDate.isNullOrEmpty()) {
            endDate.toTimestamp(true)
        } else 0L

        EventContentState(
            name = name.orEmpty(),
            location = location.orEmpty(),
            description = description.orEmpty(),
            isAllDay = !startDate.isNullOrEmpty() || !endDate.isNullOrEmpty(),
            startTimestamp = startTimestamp,
            endTimestamp = endTimestamp,
        )
    } catch (_: Throwable) {
        null
    }
}