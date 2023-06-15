package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.phone

sealed interface PhoneContentEvent {
    data class PhoneChanged(val phone: String) : PhoneContentEvent
}
