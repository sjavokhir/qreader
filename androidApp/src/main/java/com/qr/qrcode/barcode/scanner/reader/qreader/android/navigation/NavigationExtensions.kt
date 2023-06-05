package com.qr.qrcode.barcode.scanner.reader.qreader.android.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.gotoUrl
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.shareText
import com.qr.qrcode.barcode.scanner.reader.qreader.shared.appUrl

fun NavHostController.navigateTo(destination: NavigationTree) {
    when (destination) {
        NavigationTree.RateUs -> context.gotoUrl(appUrl)
        NavigationTree.TellFriends -> {
            context.shareText(context.getString(R.string.share_description))
        }

        else -> {
            navigate(destination.route)
        }
    }
}

fun NavHostController.bottomNavigate(destination: BottomNavigationTree) {
    navigate(destination.route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}

fun NavHostController.navigateTo(
    destination: String,
    route: String? = null
) {
    navigate(route ?: destination)
}