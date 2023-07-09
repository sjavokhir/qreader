package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.history

sealed class HistoryEvent {
    data class QueryChanged(val query: String) : HistoryEvent()
    data class GetHistory(val page: Int) : HistoryEvent()
}
