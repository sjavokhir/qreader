package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.website

import com.qr.qrcode.barcode.scanner.reader.qreader.core.extensions.isUrlValid
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

class WebsiteContentViewModel : KMMViewModel(), KoinComponent {

    private val stateData = MutableStateFlow(viewModelScope, WebsiteContentState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    fun onEvent(event: WebsiteContentEvent) {
        when (event) {
            is WebsiteContentEvent.WebsiteChanged -> onWebsiteChanged(website = event.website)
        }
    }

    private fun onWebsiteChanged(website: String) {
        stateData.update {
            it.copy(
                website = website,
                isEnabled = website.isUrlValid()
            )
        }
    }

//    fun getContent(): QrGenerateContent {
//        return QrGenerateContent(
//            qrContent = currentState.website,
//            formattedContent = "${AppStrings.website}: ${currentState.website}"
//        )
//    }
}