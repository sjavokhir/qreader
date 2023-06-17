package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.generateContents

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateHeaderType
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateType

data class GenerateContentsState(
    val contents: Map<GenerateHeaderType, List<GenerateType>> = emptyMap(),
    val isLoading: Boolean = false
)