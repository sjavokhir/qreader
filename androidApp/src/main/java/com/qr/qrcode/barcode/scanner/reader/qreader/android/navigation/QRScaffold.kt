package com.qr.qrcode.barcode.scanner.reader.qreader.android.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
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

    // 👇 only for debugging, you shouldn't use currentBackStack API as it is restricted by annotation
    navController.currentBackStack.collectAsState().value.print()

//    val bottomSheetNavigator = rememberBottomSheetNavigator()
//    navController.navigatorProvider += bottomSheetNavigator
//
//    // 👇 ModalBottomSheetLayout is only needed if some destination is bottom sheet styled
//    ModalBottomSheetLayout(
//        bottomSheetNavigator = bottomSheetNavigator,
//        sheetShape = RoundedCornerShape(16.dp)
//    ) {
//        Scaffold(
//            topBar = { topBar(destination, navBackStackEntry) },
//            bottomBar = { bottomBar(destination) },
//            containerColor = Color.Transparent,
//            contentColor = MaterialTheme.colorScheme.onBackground,
//            content = content
//        )
//    }

    Scaffold(
        topBar = { topBar(destination, navBackStackEntry) },
        bottomBar = { bottomBar(destination) },
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        content = content
    )
}

private fun Collection<NavBackStackEntry>.print(prefix: String = "stack") {
    val stack = map { it.destination.route }.toTypedArray().contentToString()
    println("$prefix = $stack")
}