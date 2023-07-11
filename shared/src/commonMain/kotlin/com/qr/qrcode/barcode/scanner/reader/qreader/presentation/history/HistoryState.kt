package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.history

import com.qr.qrcode.barcode.scanner.reader.qreader.data.database.entity.HistoryEntity

data class HistoryState(
    val scannedQuery: String = "",
    val createdQuery: String = "",
    val scannedHistory: List<HistoryEntity> = emptyList(),
    val createdHistory: List<HistoryEntity> = emptyList(),
    val isScanned: Boolean = true,
    val isLoading: Boolean = false
)
