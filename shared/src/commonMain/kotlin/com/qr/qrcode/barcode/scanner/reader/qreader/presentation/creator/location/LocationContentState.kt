package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.location

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.QrData

data class LocationContentState(
    val latitude: String = "",
    val longitude: String = "",
    val isEnabled: Boolean = false,
    val isSetEncoded: Boolean = false
) : QrData {

    override fun encode(): String = "geo:$latitude,$longitude"

    override fun decode(): String = "$latitude, $longitude"
}

fun String.toLocationContent(): LocationContentState? {
    return try {
        if (startsWith("geo:")) {
            val (latitude, longitude) = removePrefix("geo:").split(",")

            LocationContentState(latitude, longitude)
        } else null
    } catch (_: Throwable) {
        null
    }
}
