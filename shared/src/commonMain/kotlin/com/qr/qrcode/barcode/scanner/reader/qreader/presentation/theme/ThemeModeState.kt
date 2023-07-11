package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.theme

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.ThemeMode

data class ThemeModeState(
    val selectedTheme: ThemeMode = ThemeMode.System,
    val themeModes: List<ThemeMode> = emptyList()
)
