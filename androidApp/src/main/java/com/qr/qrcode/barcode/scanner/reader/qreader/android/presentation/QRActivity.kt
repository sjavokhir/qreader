package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.qr.qrcode.barcode.scanner.reader.qreader.android.navigation.QRApp
import com.qr.qrcode.barcode.scanner.reader.qreader.data.datastore.AppStore
import com.qr.qrcode.barcode.scanner.reader.qreader.shared.Event
import com.qr.qrcode.barcode.scanner.reader.qreader.shared.EventChannel
import org.koin.android.ext.android.inject

class QRActivity : ComponentActivity() {

    private val appStore by inject<AppStore>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            var themeMode by remember { mutableStateOf(appStore.getSelectedThemeMode()) }

            val event = EventChannel.receiveEvent().collectAsStateWithLifecycle(
                initialValue = Event.Idle,
                lifecycle = lifecycle
            ).value

            when (event) {
                Event.Idle -> {}
                is Event.ThemeModeChanged -> {
                    themeMode = event.themeMode
                }
            }

            QRApp(
                isOnBoarding = appStore.isOnBoarding(),
                themeMode = themeMode
            )
        }
    }
}