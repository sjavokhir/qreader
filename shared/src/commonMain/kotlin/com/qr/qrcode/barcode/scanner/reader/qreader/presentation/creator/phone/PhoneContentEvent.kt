package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.phone

sealed class PhoneContentEvent {
    data class Encoded(val value: String) : PhoneContentEvent()
    data class PhoneChanged(val phone: String) : PhoneContentEvent()
}
