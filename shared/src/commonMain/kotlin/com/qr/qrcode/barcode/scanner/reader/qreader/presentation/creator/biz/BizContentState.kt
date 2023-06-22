package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.biz

data class BizContentState(
    val firstName: String = "",
    val lastName: String = "",
    val job: String = "",
    val company: String = "",
    val phone: String = "",
    val email: String = "",
    val address: String = "",
    val isEnabled: Boolean = false,
    val generateText: String = ""
)