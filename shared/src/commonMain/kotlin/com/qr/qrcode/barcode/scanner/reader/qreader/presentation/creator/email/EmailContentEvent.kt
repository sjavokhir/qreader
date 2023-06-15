package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.email

sealed interface EmailContentEvent {
    data class EmailChanged(val email: String) : EmailContentEvent
}
