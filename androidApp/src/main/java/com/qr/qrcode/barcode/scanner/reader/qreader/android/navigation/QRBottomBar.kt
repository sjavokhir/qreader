package com.qr.qrcode.barcode.scanner.reader.qreader.android.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRNavigationBar
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRNavigationBarItem
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.CreatorScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.DirectionDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.HistoryScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.ScannerScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.SettingsScreenDestination
import com.ramcosta.composedestinations.utils.isRouteOnBackStack

@Composable
fun QRBottomBar(
    navController: NavHostController
) {
    QRNavigationBar {
        BottomBarItem.values().forEach { destination ->
            val isCurrentDestOnBackStack = navController.isRouteOnBackStack(destination.direction)

            QRNavigationBarItem(
                icon = painterResource(id = destination.icon),
                label = stringResource(id = destination.label),
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

enum class BottomBarItem(
    val direction: DirectionDestination,
    @DrawableRes val icon: Int,
    @StringRes val label: Int
) {
    Scanner(
        direction = ScannerScreenDestination,
        icon = R.drawable.ic_scanner,
        label = R.string.scanner
    ),
    Creator(
        direction = CreatorScreenDestination,
        icon = R.drawable.ic_creator,
        label = R.string.creator
    ),
    History(
        direction = HistoryScreenDestination,
        icon = R.drawable.ic_history,
        label = R.string.history
    ),
    Settings(
        direction = SettingsScreenDestination,
        icon = R.drawable.ic_settings,
        label = R.string.settings
    )
}