package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.sms

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.QrData

data class SmsContentState(
    val phone: String = "",
    val message: String = "",
    val useMMS: Boolean = false,
    val isEnabled: Boolean = false
) : QrData {

    override fun encode(): String {
        return "${if (useMMS) "MMS" else "SMS"}:" +
                "$phone${if (message.isNotEmpty()) ":$message" else ""}"
    }
}
