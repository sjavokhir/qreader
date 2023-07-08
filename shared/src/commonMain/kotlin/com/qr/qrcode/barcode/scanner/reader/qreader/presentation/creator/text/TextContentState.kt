package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.text

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.QrData

data class TextContentState(
    val text: String = "",
    val isEnabled: Boolean = false
) : QrData {

    override fun encode(): String = text
}