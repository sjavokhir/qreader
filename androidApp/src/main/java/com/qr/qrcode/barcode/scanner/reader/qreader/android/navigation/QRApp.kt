package com.qr.qrcode.barcode.scanner.reader.qreader.android.navigation

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRNavigationBar
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRNavigationBarItem
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRTopAppBar
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.theme.QRTheme
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.EntryType

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QRApp(
    entry: EntryType,
    appState: QRAppState = rememberQRAppState()
) {
    QRTheme {
        Scaffold(
            topBar = {
                QRTopAppBar(
                    onNavigateUp = { appState.navController.navigateUp() },
                    currentDestination = appState.currentDestination
                )
            },
            bottomBar = {
                QRBottomBar(
                    destinations = appState.topLevelDestinations,
                    onNavigateToRoute = appState.navController::bottomNavigate,
                    currentDestination = appState.currentDestination
                )
            },
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground
        ) { padding ->
            QRNavHost(
                navController = appState.navController,
                modifier = Modifier
                    .padding(padding)
                    .consumeWindowInsets(padding),
                startDestination = when (entry) {
                    EntryType.OnBoarding -> NavigationTree.OnBoarding.route
                    EntryType.Scanner -> NavigationTree.Scanner.route
                }
            )
        }
    }
}

@Composable
private fun QRBottomBar(
    destinations: List<BottomNavigationTree>,
    onNavigateToRoute: (BottomNavigationTree) -> Unit,
    currentDestination: NavDestination?
) {
    val navigationTree = remember(currentDestination) {
        currentDestination?.route.navigationTree()
    }

    when (navigationTree) {
        NavigationTree.OnBoarding, NavigationTree.Premium -> {
            // Nothing
        }

        else -> {
            QRNavigationBar {
                destinations.forEach { destination ->
                    val selected = currentDestination?.hierarchy?.any {
                        it.route == destination.route
                    } == true

                    QRNavigationBarItem(
                        icon = painterResource(id = destination.icon),
                        label = stringResource(id = destination.label),
                        onClick = { onNavigateToRoute(destination) },
                        selected = selected
                    )
                }
            }
        }
    }
}