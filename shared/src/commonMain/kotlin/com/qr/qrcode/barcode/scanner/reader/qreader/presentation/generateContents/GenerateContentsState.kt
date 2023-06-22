package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.generateContents

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateHeader
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateMode

data class GenerateContentsState(
    val contents: Map<GenerateHeader, List<GenerateMode>> = emptyMap(),
    val isLoading: Boolean = false
)