package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.sms

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.QrData

data class SmsContentState(
    val phone: String = "",
    val message: String = "",
    val isEnabled: Boolean = false,
    val isSetEncoded: Boolean = false
) : QrData {

    override fun encode(): String = "smsto:$phone?body=$message"

    override fun decode(): String = """
        $phone
        $message
    """.trimIndent()
}

fun String.toSmsContent(): SmsContentState? {
    return try {
        if (startsWith("smsto:")) {
            val parts = split("?body=")
            val phone = parts[0].removePrefix("smsto:")
            val message = parts[1]

            SmsContentState(
                phone = phone,
                message = message
            )
        } else null
    } catch (_: Throwable) {
        null
    }
}
