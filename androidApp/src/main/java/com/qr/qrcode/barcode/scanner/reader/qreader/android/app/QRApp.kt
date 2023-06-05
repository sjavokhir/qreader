package com.qr.qrcode.barcode.scanner.reader.qreader.android.app

import android.app.Application
import com.qr.qrcode.barcode.scanner.reader.qreader.di.initKoin
import org.koin.android.ext.koin.androidContext

class QRApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@QRApp)
        }
    }
}