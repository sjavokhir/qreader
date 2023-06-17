package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.socialMedia

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateType

data class SocialMediaContentState(
    val username: String = "",
    val type: GenerateType = GenerateType.Text,
    val isEnabled: Boolean = false,
    val generateText: String = ""
)
