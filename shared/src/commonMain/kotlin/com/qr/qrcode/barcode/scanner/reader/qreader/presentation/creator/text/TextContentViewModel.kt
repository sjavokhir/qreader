package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.text

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TextContentViewModel : KMMViewModel() {

    private val stateData = MutableStateFlow(viewModelScope, TextContentState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    fun onEvent(event: TextContentEvent) {
        when (event) {
            is TextContentEvent.TextChanged -> onTextChanged(text = event.text)
        }
    }

    private fun onTextChanged(text: String) {
        stateData.update {
            it.copy(
                text = text,
                isEnabled = text.isNotEmpty()
            )
        }
    }
}