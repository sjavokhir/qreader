package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.contact

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.QrData

data class ContactContentState(
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val address: String = "",
    val isEnabled: Boolean = false
) : QrData {

    override fun encode(): String = buildString {
        append("BEGIN:VCARD\n")
        append("VERSION:3.0\n")
        append("N:$name\n")
        append("FN:$name\n")
        append("TEL:$phone\n")
        append("EMAIL:$email\n")
        append("ADR:$address\n")
        append("END:VCARD")
    }
}