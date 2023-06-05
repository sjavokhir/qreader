package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.sound

data class SoundEffectsState(
    val isSoundEffectsChecked: Boolean = false,
    val selectedSound: Int = 1,
    val soundEffects: List<Int> = emptyList()
)
