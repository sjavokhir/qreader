package com.qr.qrcode.barcode.scanner.reader.qreader.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRTopAppBar
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.AddContentScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.CreatorScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.CustomizeScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.DateTimePickerScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.Destination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.FaqScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.FeedbackScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.HistoryScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.LanguageScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.LocationPickerScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.ManagePermissionsScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.OnBoardingScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.PremiumScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.QRCodeScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.ScannerScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.SettingsScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.SoundEffectsScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.ThemeModeScreenDestination

@Composable
fun QRTopBar(
    destination: Destination,
    navController: NavHostController
) {
    QRTopAppBar(
        title = destination.topBarTitle(),
        onNavigateUp = if (destination.shouldShowNavigateUp) {
            { navController.navigateUp() }
        } else null
    )
}

@Composable
private fun Destination.topBarTitle(): Int {
    return when (this) {
        CreatorScreenDestination -> R.string.creator
        FaqScreenDestination -> R.string.faq
        FeedbackScreenDestination -> R.string.feedback
        HistoryScreenDestination -> R.string.history
        LanguageScreenDestination -> R.string.language
        ManagePermissionsScreenDestination -> R.string.manage_permissions
        OnBoardingScreenDestination -> R.string.on_boarding
        PremiumScreenDestination -> R.string.go_pro
        ScannerScreenDestination -> R.string.scanner
        SettingsScreenDestination -> R.string.settings
        SoundEffectsScreenDestination -> R.string.sound_effects
        AddContentScreenDestination -> R.string.add_content
        DateTimePickerScreenDestination -> R.string.select_date_and_time
        LocationPickerScreenDestination -> R.string.action_select_location
        QRCodeScreenDestination -> R.string.qr_code
        CustomizeScreenDestination -> R.string.customize_qr
        ThemeModeScreenDestination -> R.string.theme
    }
}

private val Destination.shouldShowNavigateUp
    get() = when (this) {
        OnBoardingScreenDestination,
        ScannerScreenDestination,
        CreatorScreenDestination,
        HistoryScreenDestination,
        SettingsScreenDestination,
        PremiumScreenDestination -> false

        else -> true
    }