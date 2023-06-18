package com.qr.qrcode.barcode.scanner.reader.qreader.android.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.appCurrentDestinationAsState
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.Destination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.startAppDestination
import com.ramcosta.composedestinations.spec.Route

@Composable
fun QRScaffold(
    startRoute: Route,
    navController: NavHostController,
    topBar: @Composable (Destination, NavBackStackEntry?) -> Unit,
    bottomBar: @Composable (Destination) -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
    val destination = navController.appCurrentDestinationAsState().value
        ?: startRoute.startAppDestination
    val navBackStackEntry = navController.currentBackStackEntry

    Scaffold(
        topBar = { topBar(destination, navBackStackEntry) },
        bottomBar = { bottomBar(destination) },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        content = content
    )
}