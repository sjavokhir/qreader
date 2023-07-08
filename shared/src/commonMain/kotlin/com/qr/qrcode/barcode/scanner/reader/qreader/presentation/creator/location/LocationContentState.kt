package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.location

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.QrData

data class LocationContentState(
    val latitude: Double? = null,
    val longitude: Double? = null,
    val isEnabled: Boolean = false
) : QrData {

    override fun encode(): String = "GEO:$latitude,$longitude"
}
