package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.socialMedia

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateMode

sealed interface SocialMediaContentEvent {
    data class SetGenerateMode(val mode: GenerateMode) : SocialMediaContentEvent
    data class UsernameChanged(val username: String) : SocialMediaContentEvent
}
