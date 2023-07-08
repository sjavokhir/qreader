package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.sms

sealed interface SmsContentEvent {
    data class PhoneChanged(val phone: String) : SmsContentEvent
    data class MessageChanged(val message: String) : SmsContentEvent
    data class UseMMSChecked(val isChecked: Boolean) : SmsContentEvent
}
