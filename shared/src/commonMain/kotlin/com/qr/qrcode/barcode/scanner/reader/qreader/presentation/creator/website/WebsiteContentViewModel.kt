package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.website

import com.qr.qrcode.barcode.scanner.reader.qreader.core.extensions.isUrlValid
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WebsiteContentViewModel : KMMViewModel() {

    private val stateData = MutableStateFlow(viewModelScope, WebsiteContentState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    fun onEvent(event: WebsiteContentEvent) {
        when (event) {
            is WebsiteContentEvent.Encoded -> onEncoded(event.value)
            is WebsiteContentEvent.WebsiteChanged -> onWebsiteChanged(event.website)
        }
    }

    private fun onEncoded(value: String) {
        onWebsiteChanged(value)
    }

    private fun onWebsiteChanged(website: String) {
        stateData.update {
            it.copy(
                website = website,
                isEnabled = website.isUrlValid(),
                isSetEncoded = true
            )
        }
    }
}