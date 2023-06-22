package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.settings

data class SettingsState(
    val isVibrateChecked: Boolean = false,
    val isOpenWebPagesChecked: Boolean = false,
    val isChromeCustomTabsChecked: Boolean = false,
    val isBatchScanChecked: Boolean = false,
    val hasSubscription: Boolean = true
)
