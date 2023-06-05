package com.qr.qrcode.barcode.scanner.reader.qreader.di

import android.content.Context
import com.qr.qrcode.barcode.scanner.reader.qreader.data.util.Keys
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.dsl.module

actual fun platformModule() = module {
    single { createSettings(get()) }
//    single { AppDatabase(AndroidSqliteDriver(AppDatabase.Schema, get(), Constants.APP_DATABASE)) }
}

fun createSettings(context: Context): ObservableSettings {
    val delegate = context.getSharedPreferences(Keys.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    return SharedPreferencesSettings(delegate = delegate)
}
