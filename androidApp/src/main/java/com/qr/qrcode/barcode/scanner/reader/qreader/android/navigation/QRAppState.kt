package com.qr.qrcode.barcode.scanner.reader.qreader.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberQRAppState(
    navController: NavHostController = rememberNavController()
): QRAppState {
    return remember(navController) { QRAppState(navController) }
}

@Stable
class QRAppState(val navController: NavHostController) {

    val currentDestination: NavDestination?
        @Composable
        get() = navController.currentBackStackEntryAsState().value?.destination

    val topLevelDestinations: List<BottomNavigationTree> = listOf(
        BottomNavigationTree.Scanner,
        BottomNavigationTree.Creator,
        BottomNavigationTree.History,
        BottomNavigationTree.Settings
    )
}