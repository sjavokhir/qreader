package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.sound

sealed class SoundEffectsEvent {
    data class CheckSoundEffects(val isChecked: Boolean) : SoundEffectsEvent()
    data class SelectSound(val sound: Int) : SoundEffectsEvent()
}