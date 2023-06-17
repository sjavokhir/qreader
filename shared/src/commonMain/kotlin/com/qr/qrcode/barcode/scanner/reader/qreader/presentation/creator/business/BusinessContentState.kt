package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.business

data class BusinessContentState(
    val name: String = "",
    val industry: String = "",
    val phone: String = "",
    val email: String = "",
    val website: String = "",
    val address: String = "",
    val isEnabled: Boolean = false
)