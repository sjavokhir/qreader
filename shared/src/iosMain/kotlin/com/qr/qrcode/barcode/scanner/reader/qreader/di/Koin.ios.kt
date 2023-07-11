package com.qr.qrcode.barcode.scanner.reader.qreader.di

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.qr.qrcode.barcode.scanner.reader.qreader.data.util.Keys
import com.qr.qrcode.barcode.scanner.reader.qreader.db.AppDatabase
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.ObservableSettings
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

actual fun platformModule() = module {
    single { createSettings() }
    single { AppDatabase(NativeSqliteDriver(AppDatabase.Schema, Keys.APP_DATABASE)) }
}

fun createSettings(): ObservableSettings {
    val delegate = NSUserDefaults.standardUserDefaults
    return NSUserDefaultsSettings(delegate = delegate)
}