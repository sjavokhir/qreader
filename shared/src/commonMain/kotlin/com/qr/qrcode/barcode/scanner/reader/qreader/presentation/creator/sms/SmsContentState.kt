package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.sms

data class SmsContentState(
    val phone: String = "",
    val message: String = "",
    val isEnabled: Boolean = false,
    val generateText: String = ""
)
