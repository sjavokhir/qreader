package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.contact

sealed interface ContactContentEvent {
    data class FirstNameChanged(val value: String) : ContactContentEvent
    data class LastNameChanged(val value: String) : ContactContentEvent
    data class PhoneChanged(val value: String) : ContactContentEvent
    data class EmailChanged(val value: String) : ContactContentEvent
    data class WebsiteChanged(val value: String) : ContactContentEvent
    data class AddressChanged(val value: String) : ContactContentEvent
}
