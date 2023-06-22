package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.contact

data class ContactContentState(
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val address: String = "",
    val isEnabled: Boolean = false,
    val generateText: String = ""
)