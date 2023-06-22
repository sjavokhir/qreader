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
            is SocialMediaContentEvent.SetType -> onGenerateType(event.type)
            is SocialMediaContentEvent.UsernameChanged -> onUsernameChanged(event.username)
        }
    }

    private fun onGenerateType(type: GenerateMode) {
        stateData.update { it.copy(type = type) }
    }

    private fun onUsernameChanged(username: String) {
        stateData.update {
            it.copy(
                username = username,
                isEnabled = username.isNotEmpty()
            )
        }
        stateData.update { it.copy(generateText = it.generateText()) }
    }

    private fun SocialMediaContentState.generateText(): String {
        return when (type) {
            GenerateMode.Youtube -> "https://youtube.com/@"
            GenerateMode.WhatsApp -> "https://wa.me/"
            GenerateMode.Instagram -> "https://instagram.com/"
            GenerateMode.Facebook -> "https://facebook.com/"
            GenerateMode.Twitter -> "https://twitter.com/"
            GenerateMode.TikTok -> "https://tiktok.com/@"
            GenerateMode.Telegram -> "https://t.me/"
            GenerateMode.VKontakte -> "https://vk.com/"
            GenerateMode.Twitch -> "https://twitch.tv/"
            GenerateMode.LinkedIn -> "https://linkedin.com/in/"
            GenerateMode.Github -> "https://github.com/"
            GenerateMode.Medium -> "https://medium.com/"
            GenerateMode.Dribbble -> "https://dribbble.com/"
            GenerateMode.Behance -> "https://www.behance.net/"
            else -> ""
        } + username
    }
}