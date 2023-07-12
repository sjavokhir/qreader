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
            is TextContentEvent.Encoded -> onEncoded(event.value)
            is TextContentEvent.TextChanged -> onTextChanged(event.text)
        }
    }

    private fun onEncoded(value: String) {
        if (state.value.isSetEncoded) return

        onTextChanged(value)
    }

    private fun onTextChanged(text: String) {
        stateData.update {
            it.copy(
                text = text,
                isEnabled = text.isNotEmpty(),
                isSetEncoded = true
            )
        }
    }
}