package com.qr.qrcode.barcode.scanner.reader.qreader.android.core.model

import java.io.Serializable

data class EditContentModel(
    val encoded: String,
    val decoded: String
) : Serializable
