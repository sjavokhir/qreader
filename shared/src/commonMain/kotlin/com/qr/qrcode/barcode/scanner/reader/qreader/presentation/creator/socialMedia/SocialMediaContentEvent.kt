package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.socialMedia

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateType

sealed interface SocialMediaContentEvent {
    data class SetType(val type: GenerateType) : SocialMediaContentEvent
    data class UsernameChanged(val username: String) : SocialMediaContentEvent
}
