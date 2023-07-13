package com.qr.qrcode.barcode.scanner.reader.qreader.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRTopAppBar
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.AddContentScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.CreatorScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.CustomizeScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.DateTimePickerScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.Destination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.FaqScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.FeedbackScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.HistoryScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.ImageCropperScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.LanguageScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.LocationPickerScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.ManagePermissionsScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.OnBoardingScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.PremiumScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.QRCodeScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.ScannerScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.SettingsScreenDestination
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
private fun Destination.topBarTitle(): String {
    val strings = LocalStrings.current

    return when (this) {
        CreatorScreenDestination -> strings.creator
        FaqScreenDestination -> strings.faq
        FeedbackScreenDestination -> strings.feedback
        HistoryScreenDestination -> strings.history
        LanguageScreenDestination -> strings.language
        ManagePermissionsScreenDestination -> strings.managePermissions
        OnBoardingScreenDestination -> ""
        PremiumScreenDestination -> ""
        ScannerScreenDestination -> strings.scanner
        SettingsScreenDestination -> strings.settings
        AddContentScreenDestination -> strings.addContent
        DateTimePickerScreenDestination -> strings.selectDateAndTime
        LocationPickerScreenDestination -> strings.selectLocation
        QRCodeScreenDestination -> strings.qrCode
        CustomizeScreenDestination -> strings.customizeQr
        ThemeModeScreenDestination -> strings.theme
        ImageCropperScreenDestination -> strings.scanImage
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