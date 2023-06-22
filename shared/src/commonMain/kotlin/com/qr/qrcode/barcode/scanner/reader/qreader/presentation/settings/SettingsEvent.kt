package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.settings

sealed class SettingsEvent {
    data class CheckVibrate(val isChecked: Boolean) : SettingsEvent()
    data class CheckOpenWebPages(val isChecked: Boolean) : SettingsEvent()
    data class CheckBatchScan(val isChecked: Boolean) : SettingsEvent()
}