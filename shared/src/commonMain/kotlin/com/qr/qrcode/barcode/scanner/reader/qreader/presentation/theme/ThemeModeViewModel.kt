package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.theme

import com.qr.qrcode.barcode.scanner.reader.qreader.data.datastore.AppStore
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.ThemeMode
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ThemeModeViewModel : KMMViewModel(), KoinComponent {

    private val appStore by inject<AppStore>()

    private val stateData = MutableStateFlow(viewModelScope, ThemeModeState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    init {
        viewModelScope.coroutineScope.launch {
            val modes = listOf(
                ThemeMode.System,
                ThemeMode.Light,
                ThemeMode.Dark
            )

            stateData.update {
                it.copy(
                    selectedTheme = appStore.getSelectedThemeMode(),
                    themeModes = modes
                )
            }
        }
    }

    fun onEvent(event: ThemeModeEvent) {
        when (event) {
            is ThemeModeEvent.SelectThemeMode -> onThemeModeSelected(event.themeMode)
        }
    }

    private fun onThemeModeSelected(themeMode: ThemeMode) {
        appStore.setSelectedThemeMode(themeMode)

        stateData.update {
            it.copy(selectedTheme = themeMode)
        }
    }
}