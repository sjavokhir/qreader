package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.theme

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.ThemeMode

sealed class ThemeModeEvent {
    data class SelectThemeMode(val themeMode: ThemeMode) : ThemeModeEvent()
}