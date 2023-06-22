package com.qr.qrcode.barcode.scanner.reader.qreader.android.core.helpers

import androidx.appcompat.app.AppCompatDelegate
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.ThemeMode

object ThemeHelper {

    fun changeTheme(themeMode: ThemeMode) {
        when (themeMode) {
            ThemeMode.System -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }

            ThemeMode.Light -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            ThemeMode.Dark -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }
}