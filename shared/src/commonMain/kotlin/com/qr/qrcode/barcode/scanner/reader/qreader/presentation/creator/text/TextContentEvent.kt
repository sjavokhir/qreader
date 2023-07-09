package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.text

sealed class TextContentEvent {
    data class Encoded(val value: String) : TextContentEvent()
    data class TextChanged(val text: String) : TextContentEvent()
}