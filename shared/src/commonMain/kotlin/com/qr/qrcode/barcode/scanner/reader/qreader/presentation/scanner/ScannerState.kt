package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.scanner

data class ScannerState(
    val isVibrateEnabled: Boolean = false,
    val isOpenWebPagesEnabled: Boolean = false,
    val isChromeCustomTabsEnabled: Boolean = false
)
