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
                latitude = event.latitude.toDoubleOrNull()
            )

            is LocationContentEvent.LongitudeChanged -> onValueChanged(
                longitude = event.longitude.toDoubleOrNull()
            )
        }
    }

    private fun onEncoded(value: String) {
    }

    private fun onLocationChanged(location: String) {
        tryCatch {
            val (latitude, longitude) = location
                .split(",")
                .map { it.toDoubleOrNull() }

            stateData.update {
                val mLatitude = latitude ?: it.latitude
                val mLongitude = longitude ?: it.longitude

                it.copy(
                    latitude = mLatitude,
                    longitude = mLongitude,
                    isEnabled = (mLatitude ?: 0.0) > 0.0 && (mLongitude ?: 0.0) > 0.0
                )
            }
        }
    }

    private fun onValueChanged(
        latitude: Double? = null,
        longitude: Double? = null
    ) {
        stateData.update {
            val mLatitude = latitude ?: it.latitude
            val mLongitude = longitude ?: it.longitude

            it.copy(
                latitude = mLatitude,
                longitude = mLongitude,
                isEnabled = (mLatitude ?: 0.0) > 0.0 && (mLongitude ?: 0.0) > 0.0
            )
        }
    }
}