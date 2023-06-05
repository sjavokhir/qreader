package com.qr.qrcode.barcode.scanner.reader.qreader.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun QRNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        onBoardingGraph(navController)
        scannerGraph(navController)
        creatorGraph(navController)
        historyGraph(navController)
        settingsGraph(navController)
        premiumGraph(navController)
    }
}