package com.qr.qrcode.barcode.scanner.reader.qreader.core.datetime

import com.qr.qrcode.barcode.scanner.reader.qreader.core.extensions.az
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun currentTimestamp(): Long {
    return Clock.System.now().toEpochMilliseconds()
}

fun Long?.actualDateMillis(hour: Int, minute: Int): Long {
    this ?: return currentTimestamp()

    val millisecondsPerSecond = 1000L
    val millisecondsPerMinute = 60 * millisecondsPerSecond
    val millisecondsPerHour = 60 * millisecondsPerMinute

    return this + hour * millisecondsPerHour + minute * millisecondsPerMinute
}

fun Long.timestampToString(isAllDay: Boolean): String {
    val model = Instant.fromEpochMilliseconds(this).toDateTimeModel()
    return "${model.year.az()}${model.month.az()}${model.dayOfMonth.az()}" + if (!isAllDay) {
        "T${model.hour.az()}${model.minute.az()}${model.second.az()}"
    } else ""
}

fun Long.timestampToDateTime(): DateTimeModel {
    return Instant.fromEpochMilliseconds(this).toDateTimeModel()
}

private fun Instant.toDateTimeModel(): DateTimeModel {
    return toLocalDateTime(TimeZone.UTC).toDateTimeModel()
}

private fun LocalDateTime.toDateTimeModel(): DateTimeModel {
    return DateTimeModel(
        dayOfMonth = dayOfMonth,
        month = monthNumber,
        monthName = month.name,
        weekName = dayOfWeek.name,
        year = year,
        hour = hour,
        minute = minute,
        second = second
    )
}