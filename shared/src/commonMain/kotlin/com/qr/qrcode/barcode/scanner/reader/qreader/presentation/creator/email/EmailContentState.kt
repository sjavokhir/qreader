package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.email

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.QrData

data class EmailContentState(
    val email: String = "",
    val subject: String = "",
    val message: String = "",
    val isEnabled: Boolean = false
) : QrData {

    override fun encode(): String {
        return buildString {
            append("mailto:$email")

            if (listOf(subject, message).any { it.isNotEmpty() }) {
                append("?")
            }

            val querries = buildList {
                if (subject.isNotEmpty()) {
                    add("subject=$subject")
                }
                if (message.isNotEmpty()) {
                    add("body=$message")
                }
            }
            append(querries.joinToString(separator = "&"))
        }
    }
}