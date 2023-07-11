package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.settings

import com.qr.qrcode.barcode.scanner.reader.qreader.core.extensions.log
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

class SettingsViewModel : KMMViewModel(), KoinComponent {

    private val appStore by inject<AppStore>()

    private val stateData = MutableStateFlow(viewModelScope, SettingsState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    init {

        viewModelScope.coroutineScope.launch {
            appStore.isChromeCustomTabsEnabled().log()

            stateData.update {
                it.copy(
                    isVibrateChecked = appStore.isVibrateEnabled(),
                    isOpenWebPagesChecked = appStore.isOpenWebPagesEnabled(),
                    isChromeCustomTabsChecked = appStore.isChromeCustomTabsEnabled(),
                    isBatchScanChecked = appStore.isBatchScanEnabled(),
                )
            }
        }
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.CheckVibrate -> onVibrateChecked(event.isChecked)
            is SettingsEvent.CheckOpenWebPages -> onOpenWebPagesChecked(event.isChecked)
            is SettingsEvent.CheckChromeCustomTabs -> onChromeCustomTabsChecked(event.isChecked)
            is SettingsEvent.CheckBatchScan -> onBatchScanChecked(event.isChecked)
        }
    }

    private fun onVibrateChecked(isChecked: Boolean) {
        appStore.setVibrate(isChecked)

        stateData.update { it.copy(isVibrateChecked = isChecked) }
    }

    private fun onOpenWebPagesChecked(isChecked: Boolean) {
        appStore.setOpenWebPages(isChecked)

        stateData.update { it.copy(isOpenWebPagesChecked = isChecked) }
    }

    private fun onChromeCustomTabsChecked(isChecked: Boolean) {
        appStore.setChromeCustomTabs(isChecked)

        stateData.update { it.copy(isChromeCustomTabsChecked = isChecked) }
    }

    private fun onBatchScanChecked(isChecked: Boolean) {
        appStore.setBatchScan(isChecked)

        stateData.update { it.copy(isBatchScanChecked = isChecked) }
    }
}