package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.location

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.QrData

data class LocationContentState(
    val latitude: Double? = null,
    val longitude: Double? = null,
    val isEnabled: Boolean = false,
    val isSetEncoded: Boolean = false
) : QrData {

    override fun encode(): String = create(latitude, longitude)

    override fun decode(): String = "$latitude, $longitude"

    companion object {
        fun create(
            latitude: Double? = null,
            longitude: Double? = null,
        ): String {
            return "geo:$latitude,$longitude"
        }
    }
}
