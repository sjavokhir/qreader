package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.business

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.QrData

data class BusinessContentState(
    val name: String = "",
    val industry: String = "",
    val phone: String = "",
    val email: String = "",
    val website: String = "",
    val address: String = "",
    val isEnabled: Boolean = false,
    val isSetEncoded: Boolean = false
) : QrData {

    override fun encode(): String {
        return create(name, industry, phone, email, website, address)
    }

    override fun decode(): String {
        return "$name, $industry, $phone, $email, $address, $website"
    }

    companion object {
        fun create(
            name: String,
            industry: String,
            phone: String,
            email: String,
            website: String,
            address: String,
        ): String {
            return buildString {
                append("BEGIN:VCARD\n")
                append("VERSION:3.0\n")
                append("N:$name\n")
                append("FN:$name\n")
                append("ORG:$industry\n")
                append("TEL:$phone\n")
                append("EMAIL:$email\n")
                append("ADR:$address\n")
                append("URL:$website\n")
                append("END:VCARD")
            }
        }
    }
}