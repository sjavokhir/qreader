package com.qr.qrcode.barcode.scanner.reader.qreader.core.extensions

import com.qr.qrcode.barcode.scanner.reader.qreader.core.datetime.currentTimestamp

fun Any?.log() {
    println("LOG_TAG: ${this?.toString()}")
}

fun tryCatch(onTryAction: () -> Unit) {
    try {
        onTryAction()
    } catch (_: Throwable) {
    }
}

inline fun shouldRequest(
    timestamp: Long,
    delay: Long,
    onChangeAction: (Long) -> Unit
): Boolean {
    val currentTimestamp = currentTimestamp()
    if (currentTimestamp - timestamp < delay) return false
    onChangeAction(currentTimestamp)
    return true
}