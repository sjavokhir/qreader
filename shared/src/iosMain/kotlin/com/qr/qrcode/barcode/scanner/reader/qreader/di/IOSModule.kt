package com.qr.qrcode.barcode.scanner.reader.qreader.di

import com.qr.qrcode.barcode.scanner.reader.qreader.data.datastore.AppStore
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class IOSModule : KoinComponent {
    val appStore: AppStore by inject()

    fun initKoin() = initKoin {}
}