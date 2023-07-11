package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.history

sealed class HistoryEvent {
    data class PageChanged(val page: Int) : HistoryEvent()
    data class QueryChanged(
        val isScanned: Boolean,
        val query: String,
    ) : HistoryEvent()
}
