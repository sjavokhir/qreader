package com.qr.qrcode.barcode.scanner.reader.qreader.android.navigation

import androidx.navigation.NavHostController
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.NavGraphs
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.DirectionDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popBackStack
import com.ramcosta.composedestinations.navigation.popUpTo

fun NavHostController.bottomNavigateTo(
    isCurrentDestOnBackStack: Boolean,
    direction: DirectionDestination,
) {
    if (isCurrentDestOnBackStack) {
        // When we click again on a bottom bar item and it was already selected
        // we want to pop the back stack until the initial destination of this bottom bar item
        popBackStack(direction, false)
        return
    }

    navigate(direction) {
        // Pop up to the root of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(NavGraphs.root) {
            saveState = true
        }

        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}

fun DestinationsNavigator.bottomNavigateTo(
    direction: DirectionDestination
) {
    navigate(direction) {
        // Pop up to the root of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(NavGraphs.root) {
            saveState = true
        }

        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}