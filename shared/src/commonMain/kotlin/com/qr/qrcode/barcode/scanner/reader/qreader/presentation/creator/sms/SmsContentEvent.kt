package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.sms

sealed class SmsContentEvent {
    data class Encoded(val value: String) : SmsContentEvent()
    data class PhoneChanged(val phone: String) : SmsContentEvent()
    data class MessageChanged(val message: String) : SmsContentEvent()
}
