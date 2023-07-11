package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.socialMedia

import com.qr.qrcode.barcode.scanner.reader.qreader.core.extensions.tryCatch
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
        tryCatch {
            detectSocialMedia(value)?.let { info ->
                setGenerateMode(info.first)
                onUsernameChanged(info.second)
            }
        }
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

    private fun detectSocialMedia(link: String): Pair<GenerateMode, String>? {
        val regexMap = mapOf(
            GenerateMode.Youtube to "https://youtube\\.com/@.*",
            GenerateMode.WhatsApp to "https://wa\\.me/.*",
            GenerateMode.Instagram to "https://instagram\\.com/.*",
            GenerateMode.Facebook to "https://facebook\\.com/.*",
            GenerateMode.Twitter to "https://twitter\\.com/.*",
            GenerateMode.TikTok to "https://tiktok\\.com/@.*",
            GenerateMode.Telegram to "https://t\\.me/.*",
            GenerateMode.VKontakte to "https://vk\\.com/.*",
            GenerateMode.Twitch to "https://twitch\\.tv/.*",
            GenerateMode.LinkedIn to "https://linkedin\\.com/in/.*",
            GenerateMode.Github to "https://github\\.com/.*",
            GenerateMode.Medium to "https://medium\\.com/.*",
            GenerateMode.Dribbble to "https://dribbble\\.com/.*",
            GenerateMode.Behance to "https://behance\\.net/.*",
        )

        for ((mode, regex) in regexMap) {
            val matchResult = Regex(regex, RegexOption.IGNORE_CASE).find(link)
            if (matchResult != null) {
                val username = matchResult.groupValues[1]
                return mode to username
            }
        }

        return null
    }
}