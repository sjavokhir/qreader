package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.qr.qrcode.barcode.scanner.reader.qreader.android.navigation.QRApp
import com.qr.qrcode.barcode.scanner.reader.qreader.data.datastore.AppStore
import org.koin.android.ext.android.inject

class QRActivity : ComponentActivity() {

    private val appStore by inject<AppStore>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent { QRApp(entry = appStore.getEntryType()) }
    }
}