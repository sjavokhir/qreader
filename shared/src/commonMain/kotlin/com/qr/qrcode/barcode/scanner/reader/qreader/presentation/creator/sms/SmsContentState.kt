package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.sms

data class SmsContentState(
    val message: String = "",
    val phone: String = "",
    val isEnabled: Boolean = false
)
