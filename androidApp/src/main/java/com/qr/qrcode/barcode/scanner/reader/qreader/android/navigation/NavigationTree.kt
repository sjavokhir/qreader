package com.qr.qrcode.barcode.scanner.reader.qreader.android.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R

enum class NavigationTree(
    val route: String,
    @StringRes val title: Int
) {
    OnBoarding(
        route = "on_boarding",
        title = R.string.on_boarding
    ),
    Scanner(
        route = "scanner",
        title = R.string.scanner
    ),
    Creator(
        route = "creator",
        title = R.string.creator
    ),
    History(
        route = "history",
        title = R.string.history
    ),
    Settings(
        route = "settings",
        title = R.string.settings
    ),
    SoundEffects(
        route = "sound_effects",
        title = R.string.sound_effects
    ),
    Language(
        route = "language",
        title = R.string.language
    ),
    Feedback(
        route = "feedback",
        title = R.string.feedback
    ),
    FrequentlyAskedQuestions(
        route = "frequently_asked_questions",
        title = R.string.faq
    ),
    ManagePermissions(
        route = "manage_permissions",
        title = R.string.manage_permissions
    ),
    ManageSubscription(
        route = "manage_subscription",
        title = R.string.manage_subscription
    ),
    RateUs(
        route = "rate_us",
        title = R.string.rate_us
    ),
    TellFriends(
        route = "tell_frients",
        title = R.string.tell_friends
    ),
    AboutUs(
        route = "about_us",
        title = R.string.about_us
    ),
    Premium(
        route = "premium",
        title = R.string.go_pro
    )
}

enum class BottomNavigationTree(
    val route: String,
    @DrawableRes val icon: Int,
    @StringRes val label: Int
) {
    Scanner(
        route = "scanner",
        icon = R.drawable.ic_scanner,
        label = R.string.scanner
    ),
    Creator(
        route = "creator",
        icon = R.drawable.ic_creator,
        label = R.string.creator
    ),
    History(
        route = "history",
        icon = R.drawable.ic_history,
        label = R.string.history
    ),
    Settings(
        route = "settings",
        icon = R.drawable.ic_settings,
        label = R.string.settings
    )
}

fun String?.navigationTree(): NavigationTree {
    this ?: return NavigationTree.Scanner

    return try {
        NavigationTree.values().find { it.route == this } ?: NavigationTree.Scanner
    } catch (_: Throwable) {
        NavigationTree.Scanner
    }
}