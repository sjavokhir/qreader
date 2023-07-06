package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.ads.MobileAds
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.theme.QRTheme
import com.qr.qrcode.barcode.scanner.reader.qreader.android.navigation.QRApp
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.premium.PremiumViewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.data.datastore.AppStore
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.ThemeMode
import com.qr.qrcode.barcode.scanner.reader.qreader.shared.Event
import com.qr.qrcode.barcode.scanner.reader.qreader.shared.EventChannel
import org.koin.android.ext.android.inject

class QRActivity : ComponentActivity() {

    private val viewModel by viewModels<PremiumViewModel>()

    private val appStore by inject<AppStore>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        MobileAds.initialize(this)

        setContent {
            val hasAcknowledged = viewModel.hasAcknowledged.collectAsStateWithLifecycle().value
            val themeMode = getSelectedThemeMode()

            if (hasAcknowledged != null) {
                QRApp(
                    hasSubscription = hasAcknowledged,
                    isOnBoarding = appStore.isOnBoarding(),
                    themeMode = themeMode
                )
            } else {
                BillingLoadingContent(themeMode)
            }
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun BillingLoadingContent(themeMode: ThemeMode) {
        QRTheme(false, themeMode) {
            Scaffold(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground
            ) { padding ->
                QRBackground(
                    modifier = Modifier
                        .padding(padding)
                        .consumeWindowInsets(padding),
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(MaterialTheme.shapes.large),
                            strokeCap = StrokeCap.Round
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun getSelectedThemeMode(): ThemeMode {
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

        return themeMode
    }
}