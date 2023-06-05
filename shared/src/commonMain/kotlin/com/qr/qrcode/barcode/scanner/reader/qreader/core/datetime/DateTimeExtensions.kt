package com.qr.qrcode.barcode.scanner.reader.qreader.core.datetime

import kotlinx.datetime.Clock

fun currentUtcDate(): String {
    return Clock.System.now().toString()
}

fun currentTimestamp(): Long {
    return Clock.System.now().toEpochMilliseconds()
}