package com.qr.qrcode.barcode.scanner.reader.qreader.core.extensions

fun Int.az(): String = if (this >= 10) "" + this else "0$this"