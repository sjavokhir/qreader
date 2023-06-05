package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.language

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.LanguageType

sealed class LanguageEvent {
    data class SelectLanguage(val language: LanguageType) : LanguageEvent()
}