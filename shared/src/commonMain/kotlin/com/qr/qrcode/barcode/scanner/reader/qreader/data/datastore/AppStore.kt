package com.qr.qrcode.barcode.scanner.reader.qreader.data.datastore

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.EntryType
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.LanguageType
import com.qr.qrcode.barcode.scanner.reader.qreader.data.util.Keys
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.set

class AppStore(private val settings: ObservableSettings) {

    fun getEntryType(): EntryType {
        return EntryType.valueOf(settings.getString(Keys.ENTRY_TYPE, EntryType.OnBoarding.name))
    }

    fun setEntryType(entryType: EntryType) {
        settings[Keys.ENTRY_TYPE] = entryType.name
    }

    fun isAppLockEnabled(): Boolean {
        return settings.getBoolean(Keys.APP_LOCK, false)
    }

    fun setAppLock(isEnabled: Boolean) {
        settings[Keys.APP_LOCK] = isEnabled
    }

    fun isVibrateEnabled(): Boolean {
        return settings.getBoolean(Keys.VIBRATE, true)
    }

    fun setVibrate(isEnabled: Boolean) {
        settings[Keys.VIBRATE] = isEnabled
    }

    fun isOpenWebPagesEnabled(): Boolean {
        return settings.getBoolean(Keys.OPEN_WEB_PAGES, false)
    }

    fun setOpenWebPages(isEnabled: Boolean) {
        settings[Keys.OPEN_WEB_PAGES] = isEnabled
    }

    fun isBatchScanEnabled(): Boolean {
        return settings.getBoolean(Keys.BATCH_SCAN, false)
    }

    fun setBatchScan(isEnabled: Boolean) {
        settings[Keys.BATCH_SCAN] = isEnabled
    }

    fun getSelectedLanguage(): LanguageType {
        return try {
            val language = settings.getString(Keys.LANGUAGE, LanguageType.English.name)
            LanguageType.valueOf(language)
        } catch (_: Throwable) {
            LanguageType.English
        }
    }

    fun setSelectedLanguage(language: LanguageType) {
        settings[Keys.LANGUAGE] = language.name
    }

    fun isSoundEffectsEnabled(): Boolean {
        return settings.getBoolean(Keys.SOUND_EFFECTS, false)
    }

    fun setSoundEffects(isEnabled: Boolean) {
        settings[Keys.SOUND_EFFECTS] = isEnabled
    }

    fun getSelectedSound(): Int {
        return settings.getInt(Keys.SOUND, 1)
    }

    fun setSelectedSound(sound: Int) {
        settings[Keys.SOUND] = sound
    }
}