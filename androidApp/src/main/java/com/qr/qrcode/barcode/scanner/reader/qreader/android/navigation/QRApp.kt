package com.qr.qrcode.barcode.scanner.reader.qreader.android.navigation

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.theme.QRTheme
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.NavGraphs
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.CreatorScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.Destination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.FaqScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.FeedbackScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.HistoryScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.LanguageScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.ManagePermissionsScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.OnBoardingScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.PremiumScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.ScannerScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.SettingsScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.ThemeModeScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.LanguageType
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.ThemeMode
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.rememberNavHostEngine

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QRApp(
    hasSubscription: Boolean,
    isOnBoarding: Boolean,
    language: LanguageType,
    themeMode: ThemeMode
) {
    val engine = rememberNavHostEngine()
    val navController = engine.rememberNavController()

    val startDestination = remember(isOnBoarding) {
        if (isOnBoarding) {
            OnBoardingScreenDestination
        } else {
            ScannerScreenDestination
        }
    }

    QRTheme(
        hasSubscription = hasSubscription,
        language = language,
        themeMode = themeMode
    ) {
        QRScaffold(
            navController = navController,
            startRoute = startDestination,
            topBar = { destination, _ ->
                if (destination.shouldShowTopBar) {
                    QRTopBar(destination, navController)
                }
            },
            bottomBar = { destination ->
                QRBottomBar(
                    show = destination.shouldShowBottomBar,
                    showAds = destination.shouldShowAds,
                    hasSubscription = hasSubscription,
                    navController = navController
                )
            }
        ) { padding ->
            DestinationsNavHost(
                engine = engine,
                navController = navController,
                navGraph = NavGraphs.root,
                modifier = Modifier
                    .padding(padding)
                    .consumeWindowInsets(padding),
                startRoute = startDestination
            )
        }
    }
}

private val Destination.shouldShowTopBar
    get() = when (this) {
        OnBoardingScreenDestination,
        PremiumScreenDestination -> false

        else -> true
    }

private val Destination.shouldShowBottomBar
    get() = when (this) {
        ScannerScreenDestination,
        CreatorScreenDestination,
        HistoryScreenDestination,
        SettingsScreenDestination,
        ThemeModeScreenDestination,
        LanguageScreenDestination,
        FeedbackScreenDestination,
        FaqScreenDestination,
        ManagePermissionsScreenDestination -> true

        else -> false
    }

private val Destination.shouldShowAds
    get() = when (this) {
        OnBoardingScreenDestination,
        PremiumScreenDestination -> false

        else -> true
    }