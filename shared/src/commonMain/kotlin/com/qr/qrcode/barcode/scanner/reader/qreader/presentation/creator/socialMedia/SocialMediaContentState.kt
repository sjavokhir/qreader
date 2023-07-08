package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.socialMedia

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.QrData
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateMode

data class SocialMediaContentState(
    val username: String = "",
    val mode: GenerateMode = GenerateMode.Text,
    val isEnabled: Boolean = false
) : QrData {

    override fun encode(): String {
        return when (mode) {
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
