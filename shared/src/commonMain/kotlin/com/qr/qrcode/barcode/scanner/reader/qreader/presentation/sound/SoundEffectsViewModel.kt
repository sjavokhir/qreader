package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.sound

import com.qr.qrcode.barcode.scanner.reader.qreader.data.datastore.AppStore
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SoundEffectsViewModel : KMMViewModel(), KoinComponent {

    private val appStore by inject<AppStore>()

    private val stateData = MutableStateFlow(viewModelScope, SoundEffectsState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

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

    fun onEvent(event: SoundEffectsEvent) {
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