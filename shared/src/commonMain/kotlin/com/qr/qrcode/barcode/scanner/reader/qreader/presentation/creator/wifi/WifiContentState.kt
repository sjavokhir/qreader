package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.wifi

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.QrData

data class WifiContentState(
    val networkName: String = "",
    val password: String = "",
    val authentication: Authentication = Authentication.WEP,
    val isHidden: Boolean = false,
    val isEnabled: Boolean = false,
    val isSetEncoded: Boolean = false
) : QrData {

    val authentications: List<Authentication>
        get() = listOf(
            Authentication.WEP,
            Authentication.WPA_WPA2,
            Authentication.OPEN
        )

    enum class Authentication {
        WEP,
        WPA_WPA2 {
            override fun toString(): String = "WPA"
        },
        OPEN {
            override fun toString(): String = "nopass"
        }
    }

    override fun encode(): String = buildString {
        append("WIFI:")
        append("S:$networkName;")
        append("T:$authentication;")
        append("P:$password;")
        append("H:$isHidden;")
    }

    override fun decode(): String = "$networkName, ${authentication.name}"
}
