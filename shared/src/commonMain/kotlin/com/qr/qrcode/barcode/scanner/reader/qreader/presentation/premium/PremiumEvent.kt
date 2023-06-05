package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.premium

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.PriceType

sealed class PremiumEvent {
    data class SelectPrice(val price: PriceType) : PremiumEvent()
}