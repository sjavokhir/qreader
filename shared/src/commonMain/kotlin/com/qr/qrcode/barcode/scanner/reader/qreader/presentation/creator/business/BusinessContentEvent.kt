package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.business

sealed interface BusinessContentEvent {
    data class NameChanged(val name: String) : BusinessContentEvent
    data class IndustryChanged(val industry: String) : BusinessContentEvent
    data class PhoneChanged(val phone: String) : BusinessContentEvent
    data class EmailChanged(val email: String) : BusinessContentEvent
    data class WebsiteChanged(val website: String) : BusinessContentEvent
    data class AddressChanged(val address: String) : BusinessContentEvent
}
