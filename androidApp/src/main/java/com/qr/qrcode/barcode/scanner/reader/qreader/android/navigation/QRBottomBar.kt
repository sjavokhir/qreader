package com.qr.qrcode.barcode.scanner.reader.qreader.android.navigation

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRNavigationBar
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRNavigationBarItem
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.CreatorScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.DirectionDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.HistoryScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.ScannerScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.SettingsScreenDestination
import com.ramcosta.composedestinations.utils.isRouteOnBackStack

@Composable
fun QRBottomBar(
    show: Boolean,
    showAds: Boolean,
    hasSubscription: Boolean,
    navController: NavHostController
) {
    QRNavigationBar(
        show = show,
        showAds = showAds,
        hasSubscription = hasSubscription
    ) {
        bottomBarItems().forEach { destination ->
            val isCurrentDestOnBackStack = navController.isRouteOnBackStack(destination.direction)

            QRNavigationBarItem(
                icon = painterResource(id = destination.icon),
                label = destination.label,
                onClick = {
                    navController.bottomNavigateTo(
                        isCurrentDestOnBackStack,
                        destination.direction
                    )
                },
                selected = isCurrentDestOnBackStack
            )
        }
    }
}

@Composable
fun bottomBarItems(): List<BottomBarItem> {
    val strings = LocalStrings.current

    return listOf(
        BottomBarItem(
            direction = ScannerScreenDestination,
            icon = R.drawable.ic_scanner,
            label = strings.scanner
        ),
        BottomBarItem(
            direction = CreatorScreenDestination,
            icon = R.drawable.ic_creator,
            label = strings.creator
        ),
        BottomBarItem(
            direction = HistoryScreenDestination,
            icon = R.drawable.ic_history,
            label = strings.history
        ),
        BottomBarItem(
            direction = SettingsScreenDestination,
            icon = R.drawable.ic_settings,
            label = strings.settings
        )
    )
}

data class BottomBarItem(
    val direction: DirectionDestination,
    @DrawableRes val icon: Int,
    val label: String
)