package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.socialMedia

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateMode

sealed interface SocialMediaContentEvent {
    data class SetType(val type: GenerateMode) : SocialMediaContentEvent
    data class UsernameChanged(val username: String) : SocialMediaContentEvent
}
