package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.website

sealed class WebsiteContentEvent {
    data class Encoded(val value: String) : WebsiteContentEvent()
    data class WebsiteChanged(val website: String) : WebsiteContentEvent()
}
