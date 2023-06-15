package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.wifi

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.common.TopicModel

sealed interface WifiContentEvent {
    data class NetworkNameChanged(val name: String) : WifiContentEvent
    data class PasswordChanged(val password: String) : WifiContentEvent
    data class SelectType(val type: TopicModel) : WifiContentEvent
}
