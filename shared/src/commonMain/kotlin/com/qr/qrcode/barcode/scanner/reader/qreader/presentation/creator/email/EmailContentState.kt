package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.email

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.QrData

data class EmailContentState(
    val email: String = "",
    val subject: String = "",
    val message: String = "",
    val isEnabled: Boolean = false,
    val isSetEncoded: Boolean = false
) : QrData {

    override fun encode(): String = create(email, subject, message)

    override fun decode(): String = if (message.isNotEmpty()) {
        "$message ($email)"
    } else email

    companion object {
        fun create(
            email: String,
            subject: String,
            message: String,
        ): String {
            return "mailto:$email?subject=$subject?body=$message"
        }
    }
}