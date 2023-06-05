package com.qr.qrcode.barcode.scanner.reader.qreader.shared

import kotlinx.coroutines.Dispatchers
import java.util.UUID

actual val developerUrl: String
    get() = "https://play.google.com/store/apps/details?id=com.toptop.provider.android"

actual val appUrl: String
    get() = "https://play.google.com/store/apps/details?id=com.toptop.provider.android"

actual val appVersion: String
    get() = "1.0"

actual fun randomUUID() = UUID.randomUUID().toString()

actual val mainDispatcher = Dispatchers.Main
actual val ioDispatcher = Dispatchers.IO