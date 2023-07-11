package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.email

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.QrData

data class EmailContentState(
    val email: String = "",
    val subject: String = "",
    val message: String = "",
    val isEnabled: Boolean = false,
    val isSetEncoded: Boolean = false
) : QrData {

    override fun encode(): String = "mailto:$email?subject=$subject?body=$message"

    override fun decode(): String = buildString {
        if (message.isNotEmpty()) {
            append("$message ($email)")
        } else {
            append(email)
        }
    }
}