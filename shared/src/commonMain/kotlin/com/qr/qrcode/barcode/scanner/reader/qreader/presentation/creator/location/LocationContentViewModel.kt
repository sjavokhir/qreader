package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.location

import com.qr.qrcode.barcode.scanner.reader.qreader.core.extensions.tryCatch
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LocationContentViewModel : KMMViewModel() {

    private val stateData = MutableStateFlow(viewModelScope, LocationContentState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    fun onEvent(event: LocationContentEvent) {
        when (event) {
            is LocationContentEvent.Encoded -> onEncoded(event.value)
            is LocationContentEvent.LocationChanged -> onLocationChanged(event.location)

            is LocationContentEvent.LatitudeChanged -> onValueChanged(
                latitude = event.latitude
            )

            is LocationContentEvent.LongitudeChanged -> onValueChanged(
                longitude = event.longitude
            )
        }
    }

    private fun onEncoded(value: String) {
        tryCatch {
            if (value.startsWith("geo:")) {
                val (latitude, longitude) = value
                    .removePrefix("geo:")
                    .split(",")

                onValueChanged(latitude, longitude)
            }
        }
    }

    private fun onLocationChanged(location: String) {
        tryCatch {
            val (latitude, longitude) = location.split(",")
            onValueChanged(latitude, longitude)
        }
    }

    private fun onValueChanged(
        latitude: String? = null,
        longitude: String? = null
    ) {
        stateData.update {
            val mLatitude = latitude ?: it.latitude
            val mLongitude = longitude ?: it.longitude

            it.copy(
                latitude = mLatitude,
                longitude = mLongitude,
                isEnabled = mLatitude.isNotEmpty() && mLongitude.isNotEmpty(),
                isSetEncoded = true
            )
        }
    }
}