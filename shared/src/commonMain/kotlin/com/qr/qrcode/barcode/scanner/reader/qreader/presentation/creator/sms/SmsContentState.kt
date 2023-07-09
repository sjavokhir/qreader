package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.sms

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.QrData

data class SmsContentState(
    val phone: String = "",
    val message: String = "",
    val isEnabled: Boolean = false,
    val isSetEncoded: Boolean = false
) : QrData {

    override fun encode(): String = create(phone, message)

    override fun decode(): String = "$message ($phone)"

    companion object {
        fun create(
            phone: String,
            message: String,
        ): String {
            return "smsto:$phone?body=$message"
        }
    }
}
