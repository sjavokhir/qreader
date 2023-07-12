package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.qrCode

import com.qr.qrcode.barcode.scanner.reader.qreader.data.database.dao.HistoryDao
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

class QRCodeViewModel : KMMViewModel(), KoinComponent {

    private val appStore by inject<AppStore>()
    private val historyDao by inject<HistoryDao>()

    private val stateData = MutableStateFlow(viewModelScope, QRCodeState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    init {
        viewModelScope.coroutineScope.launch {
            stateData.update {
                it.copy(isChromeCustomTabsEnabled = appStore.isChromeCustomTabsEnabled())
            }
        }
    }

    fun onEvent(event: QRCodeEvent) {
        when (event) {
            is QRCodeEvent.Insert -> insert(event)
            is QRCodeEvent.Delete -> delete(event.id)
        }
    }

    private fun insert(event: QRCodeEvent.Insert) {
        viewModelScope.coroutineScope.launch {
            historyDao.insert(
                id = event.id,
                isScanned = event.isScanned,
                generateMode = event.mode,
                encoded = event.encoded,
                decoded = event.decoded,
                customize = event.customize
            )
        }
    }

    private fun delete(id: String) {
        viewModelScope.coroutineScope.launch {
            historyDao.delete(id)
        }
    }
}