package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.settings

import com.qr.qrcode.barcode.scanner.reader.qreader.data.datastore.AppStore
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.base.BaseViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SettingsViewModel : BaseViewModel<SettingsState, SettingsEvent>(SettingsState()),
    KoinComponent {

    private val appStore by inject<AppStore>()

    init {
        viewModelScope.coroutineScope.launch {
            stateData.update {
                it.copy(
                    isAppLockChecked = appStore.isAppLockEnabled(),
                    isVibrateChecked = appStore.isVibrateEnabled(),
                    isOpenWebPagesChecked = appStore.isOpenWebPagesEnabled(),
                    isBatchScanChecked = appStore.isBatchScanEnabled()
                )
            }
        }
    }

    override fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.CheckAppLock -> onCheckedAppLock(event.isChecked)
            is SettingsEvent.CheckVibrate -> onCheckedVibrate(event.isChecked)
            is SettingsEvent.CheckOpenWebPages -> onCheckedOpenWebPages(event.isChecked)
            is SettingsEvent.CheckBatchScan -> onCheckedBatchScan(event.isChecked)
        }
    }

    private fun onCheckedAppLock(isChecked: Boolean) {
        appStore.setAppLock(isChecked)

        stateData.update { it.copy(isAppLockChecked = isChecked) }
    }

    private fun onCheckedVibrate(isChecked: Boolean) {
        appStore.setVibrate(isChecked)

        stateData.update { it.copy(isVibrateChecked = isChecked) }
    }

    private fun onCheckedOpenWebPages(isChecked: Boolean) {
        appStore.setOpenWebPages(isChecked)

        stateData.update { it.copy(isOpenWebPagesChecked = isChecked) }
    }

    private fun onCheckedBatchScan(isChecked: Boolean) {
        appStore.setBatchScan(isChecked)

        stateData.update { it.copy(isBatchScanChecked = isChecked) }
    }
}