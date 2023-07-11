package com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common

interface QrData {

    fun encode(): String

    fun decode(): String
}