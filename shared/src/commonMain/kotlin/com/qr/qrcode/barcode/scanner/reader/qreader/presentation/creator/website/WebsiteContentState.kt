package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.website

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.QrData

data class WebsiteContentState(
    val website: String = "",
    val isEnabled: Boolean = false,
    val isSetEncoded: Boolean = false
) : QrData {

    override fun encode(): String = website

    override fun decode(): String = encode()

    override fun isNotBlank(): Boolean = website.isNotEmpty()
}
