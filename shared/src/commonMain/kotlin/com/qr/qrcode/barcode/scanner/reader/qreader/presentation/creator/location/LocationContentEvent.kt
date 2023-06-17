package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.location

sealed interface LocationContentEvent {
    data class LocationChanged(val location: String) : LocationContentEvent
    data class LatitudeChanged(val latitude: String) : LocationContentEvent
    data class LongitudeChanged(val longitude: String) : LocationContentEvent
}
