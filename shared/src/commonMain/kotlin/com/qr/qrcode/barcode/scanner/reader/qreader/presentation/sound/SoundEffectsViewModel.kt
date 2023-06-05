package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.sound

import com.qr.qrcode.barcode.scanner.reader.qreader.data.datastore.AppStore
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.base.BaseViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SoundEffectsViewModel :
    BaseViewModel<SoundEffectsState, SoundEffectsEvent>(SoundEffectsState()),
    KoinComponent {

    private val appStore by inject<AppStore>()

    init {
        viewModelScope.coroutineScope.launch {
            stateData.update {
                it.copy(
                    isSoundEffectsChecked = appStore.isSoundEffectsEnabled(),
                    selectedSound = appStore.getSelectedSound(),
                    soundEffects = listOf(1, 2, 3, 4, 5)
                )
            }
        }
    }

    override fun onEvent(event: SoundEffectsEvent) {
        when (event) {
            is SoundEffectsEvent.CheckSoundEffects -> onCheckedSoundEffects(event.isChecked)
            is SoundEffectsEvent.SelectSound -> setSelectedSound(event.sound)
        }
    }

    private fun onCheckedSoundEffects(isChecked: Boolean) {
        appStore.setSoundEffects(isChecked)

        stateData.update {
            it.copy(isSoundEffectsChecked = isChecked)
        }
    }

    private fun setSelectedSound(sound: Int) {
        appStore.setSelectedSound(sound)

        stateData.update {
            it.copy(selectedSound = sound)
        }
    }
}