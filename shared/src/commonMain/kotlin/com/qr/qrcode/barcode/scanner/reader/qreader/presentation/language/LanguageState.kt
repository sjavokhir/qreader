package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.language

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.LanguageType

data class LanguageState(
    val selectedLanguage: LanguageType = LanguageType.English,
    val languages: List<LanguageType> = emptyList()
)
