package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.wifi

sealed interface WifiContentEvent {
    data class NetworkNameChanged(val name: String) : WifiContentEvent
    data class PasswordChanged(val password: String) : WifiContentEvent
    data class SelectAuthentication(val authentication: WifiContentState.Authentication) :
        WifiContentEvent

    data class HiddenChecked(val isHidden: Boolean) : WifiContentEvent
}
