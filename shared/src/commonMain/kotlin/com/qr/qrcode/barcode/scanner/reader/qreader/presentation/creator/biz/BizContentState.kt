package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.biz

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.QrData

data class BizContentState(
    val firstName: String = "",
    val lastName: String = "",
    val company: String = "",
    val job: String = "",
    val phone: String = "",
    val email: String = "",
    val website: String = "",
    val address: String = "",
    val isEnabled: Boolean = false
) : QrData {

    override fun encode(): String = buildString {
        append("BEGIN:VCARD\n")
        append("VERSION:3.0\n")
        append("N:$lastName;$firstName\n")
        append("FN:$firstName $lastName\n")
        append("ORG:$company\n")
        append("TITLE:$job\n")
        append("TEL:$phone\n")
        append("EMAIL:$email\n")
        append("ADR:$address\n")
        append("URL:$website\n")
        append("END:VCARD")
    }
}