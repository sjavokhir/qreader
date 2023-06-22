package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.contact

sealed interface ContactContentEvent {
    data class NameChanged(val name: String) : ContactContentEvent
    data class PhoneChanged(val phone: String) : ContactContentEvent
    data class EmailChanged(val email: String) : ContactContentEvent
    data class AddressChanged(val address: String) : ContactContentEvent
}
