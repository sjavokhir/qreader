package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.phone

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.QrData

data class PhoneContentState(
    val phone: String = "",
    val isEnabled: Boolean = false,
    val isSetEncoded: Boolean = false
) : QrData {

    override fun encode(): String = create(phone)

    override fun decode(): String = phone

    companion object {
        fun create(phone: String) = "tel:$phone"
    }
}
