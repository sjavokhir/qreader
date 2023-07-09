package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.socialMedia

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateMode
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SocialMediaContentViewModel : KMMViewModel() {

    private val stateData = MutableStateFlow(viewModelScope, SocialMediaContentState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    fun onEvent(event: SocialMediaContentEvent) {
        when (event) {
            is SocialMediaContentEvent.Encoded -> onEncoded(event.value)
            is SocialMediaContentEvent.SetGenerateMode -> setGenerateMode(event.mode)
            is SocialMediaContentEvent.UsernameChanged -> onUsernameChanged(event.username)
        }
    }

    private fun onEncoded(value: String) {
    }

    private fun setGenerateMode(mode: GenerateMode) {
        stateData.update { it.copy(mode = mode) }
    }

    private fun onUsernameChanged(username: String) {
        stateData.update {
            it.copy(
                username = username,
                isEnabled = username.isNotEmpty(),
                isSetEncoded = true
            )
        }
    }
}