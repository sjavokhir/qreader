package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.qrCode

import com.qr.qrcode.barcode.scanner.reader.qreader.data.database.dao.HistoryDao
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class QRCodeViewModel : KMMViewModel(), KoinComponent {

    private val historyDao by inject<HistoryDao>()

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
                isScanned = false,
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