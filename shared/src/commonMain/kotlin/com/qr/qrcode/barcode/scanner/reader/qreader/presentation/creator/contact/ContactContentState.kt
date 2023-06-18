package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.contact

data class ContactContentState(
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",
    val email: String = "",
    val website: String = "",
    val address: String = "",
    val isEnabled: Boolean = false,
    val generateText: String = ""
)