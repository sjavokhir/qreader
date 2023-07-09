package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.history

import com.qr.qrcode.barcode.scanner.reader.qreader.data.database.entity.HistoryEntity

data class HistoryState(
    val query: String = "",
    val scannedHistory: List<HistoryEntity> = emptyList(),
    val createdHistory: List<HistoryEntity> = emptyList(),
    val isScanned: Boolean = false,
    val isLoading: Boolean = false
)
