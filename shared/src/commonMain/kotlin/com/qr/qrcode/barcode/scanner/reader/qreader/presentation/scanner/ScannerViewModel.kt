package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.scanner

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

class ScannerViewModel : KMMViewModel(), KoinComponent {

    private val appStore by inject<AppStore>()

    private val stateData = MutableStateFlow(viewModelScope, ScannerState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    init {
        viewModelScope.coroutineScope.launch {
            stateData.update {
                it.copy(
                    isVibrateEnabled = appStore.isVibrateEnabled(),
                    isOpenWebPagesEnabled = appStore.isOpenWebPagesEnabled(),
                    isChromeCustomTabsEnabled = appStore.isChromeCustomTabsEnabled()
                )
            }
        }
    }
}