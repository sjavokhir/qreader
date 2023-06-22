package com.qr.qrcode.barcode.scanner.reader.qreader.data.datastore

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.LanguageType
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.ThemeMode
import com.qr.qrcode.barcode.scanner.reader.qreader.data.util.Keys
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.set

class AppStore(private val settings: ObservableSettings) {

    fun isOnBoarding(): Boolean {
        return settings.getBoolean(Keys.IS_ON_BOARDING, true)
    }

    fun setOnBoarding(isOnBoarding: Boolean) {
        settings[Keys.IS_ON_BOARDING] = isOnBoarding
    }

    fun getSelectedThemeMode(): ThemeMode {
        return try {
            val themeMode = settings.getString(Keys.SELECTED_THEME_MODE, ThemeMode.System.name)
            ThemeMode.valueOf(themeMode)
        } catch (_: Throwable) {
            ThemeMode.System
        }
    }

    fun setSelectedThemeMode(themeMode: ThemeMode) {
        settings[Keys.SELECTED_THEME_MODE] = themeMode.name
    }

    fun getSelectedLanguage(): LanguageType {
        return try {
            val language = settings.getString(Keys.SELECTED_LANGUAGE, LanguageType.English.name)
            LanguageType.valueOf(language)
        } catch (_: Throwable) {
            LanguageType.English
        }
    }

    fun setSelectedLanguage(language: LanguageType) {
        settings[Keys.SELECTED_LANGUAGE] = language.name
    }

    fun isVibrateEnabled(): Boolean {
        return settings.getBoolean(Keys.IS_VIBRATE, true)
    }

    fun setVibrate(isEnabled: Boolean) {
        settings[Keys.IS_VIBRATE] = isEnabled
    }

    fun isOpenWebPagesEnabled(): Boolean {
        return settings.getBoolean(Keys.IS_OPEN_WEB_PAGES, false)
    }

    fun setOpenWebPages(isEnabled: Boolean) {
        settings[Keys.IS_OPEN_WEB_PAGES] = isEnabled
    }

    fun isBatchScanEnabled(): Boolean {
        return settings.getBoolean(Keys.IS_BATCH_SCAN, false)
    }

    fun setBatchScan(isEnabled: Boolean) {
        settings[Keys.IS_BATCH_SCAN] = isEnabled
    }

    fun isSoundEffectsEnabled(): Boolean {
        return settings.getBoolean(Keys.IS_SOUND_EFFECTS, false)
    }

    fun setSoundEffects(isEnabled: Boolean) {
        settings[Keys.IS_SOUND_EFFECTS] = isEnabled
    }

    fun getSelectedSound(): Int {
        return settings.getInt(Keys.SELECTED_SOUND, 1)
    }

    fun setSelectedSound(sound: Int) {
        settings[Keys.SELECTED_SOUND] = sound
    }
}