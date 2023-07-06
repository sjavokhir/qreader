package com.qr.qrcode.barcode.scanner.reader.qreader.android.navigation

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.theme.QRTheme
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.NavGraphs
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.Destination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.OnBoardingScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.PremiumScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.ScannerScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.ThemeMode
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.rememberNavHostEngine

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QRApp(
    hasSubscription: Boolean,
    isOnBoarding: Boolean,
    themeMode: ThemeMode
) {
    val engine = rememberNavHostEngine()
    val navController = engine.rememberNavController()

    val startDestination = if (isOnBoarding) {
        OnBoardingScreenDestination
    } else {
        ScannerScreenDestination
    }

    QRTheme(hasSubscription, themeMode) {
        QRScaffold(
            navController = navController,
            startRoute = startDestination,
            topBar = { destination, _ ->
                if (destination.shouldShowScaffoldElements) {
                    QRTopBar(destination, navController)
                }
            },
            bottomBar = { destination ->
                if (destination.shouldShowScaffoldElements) {
                    QRBottomBar(navController)
                }
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

private val Destination.shouldShowScaffoldElements
    get() = when (this) {
        OnBoardingScreenDestination,
        PremiumScreenDestination -> false

        else -> true
    }