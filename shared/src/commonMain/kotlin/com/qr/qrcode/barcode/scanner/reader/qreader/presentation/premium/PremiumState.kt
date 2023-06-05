package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.premium

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.PriceType

data class PremiumState(
    val selectedPrice: PriceType = PriceType.Year,
)
