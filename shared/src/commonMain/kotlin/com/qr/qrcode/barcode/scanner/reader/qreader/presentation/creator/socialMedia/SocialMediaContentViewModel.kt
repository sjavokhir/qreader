package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.socialMedia

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateType
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

    private fun onGenerateType(type: GenerateType) {
        stateData.update { it.copy(type = type) }
    }

    private fun onUsernameChanged(username: String) {
        stateData.update {
            it.copy(
                username = username,
                isEnabled = username.isNotEmpty(),
                generateText = it.generateText()
            )
        }
    }

    private fun SocialMediaContentState.generateText(): String {
        return when (type) {
            GenerateType.Youtube -> "https://youtube.com/@"
            GenerateType.WhatsApp -> "https://wa.me/"
            GenerateType.Instagram -> "https://instagram.com/"
            GenerateType.Facebook -> "https://facebook.com/"
            GenerateType.Twitter -> "https://twitter.com/"
            GenerateType.TikTok -> "https://tiktok.com/@"
            GenerateType.Telegram -> "https://t.me/"
            GenerateType.VKontakte -> "https://vk.com/"
            GenerateType.Twitch -> "https://twitch.tv/"
            GenerateType.LinkedIn -> "https://linkedin.com/in/"
            GenerateType.Github -> "https://github.com/"
            GenerateType.Medium -> "https://medium.com/"
            GenerateType.Dribbble -> "https://dribbble.com/"
            GenerateType.Behance -> "https://www.behance.net/"
            else -> ""
        } + username
    }
}