package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.qrCode

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateMode
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.customize.CustomizeState

sealed class QRCodeEvent {

    data class InsertHistory(
        val id: String,
        val mode: GenerateMode,
        val encoded: String,
        val decoded: String,
        val customize: CustomizeState,
    ) : QRCodeEvent()
}
