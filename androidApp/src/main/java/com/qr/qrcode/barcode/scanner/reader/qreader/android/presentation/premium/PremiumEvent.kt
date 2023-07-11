package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.premium

import android.app.Activity

sealed class PremiumEvent {
    data class SelectProduct(val price: String) : PremiumEvent()
    data class Buy(val activity: Activity) : PremiumEvent()
}