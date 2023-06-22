package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.socialMedia

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateMode

data class SocialMediaContentState(
    val username: String = "",
    val type: GenerateMode = GenerateMode.Text,
    val isEnabled: Boolean = false,
    val generateText: String = ""
)
