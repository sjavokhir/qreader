package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.phone

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.QrData

data class PhoneContentState(
    val phone: String = "",
    val isEnabled: Boolean = false,
    val isSetEncoded: Boolean = false
) : QrData {

    override fun encode(): String = "tel:$phone"

    override fun decode(): String = phone
}

fun String.toPhoneContent(): PhoneContentState? {
    return try {
        if (startsWith("tel:")) {
            PhoneContentState(substringAfter("tel:"))
        } else null
    } catch (_: Throwable) {
        null
    }
}
