package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.generateContents

sealed interface GenerateContentsEvent {
    object GetGenerateContents : GenerateContentsEvent
}