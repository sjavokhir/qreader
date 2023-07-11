package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.history

import com.qr.qrcode.barcode.scanner.reader.qreader.data.database.dao.HistoryDao
import com.qr.qrcode.barcode.scanner.reader.qreader.data.database.entity.HistoryEntity
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HistoryViewModel : KMMViewModel(), KoinComponent {

    private val historyDao by inject<HistoryDao>()

    private val stateData = MutableStateFlow(viewModelScope, HistoryState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    init {
        if (state.value.isScanned) {
            getHistory(true, state.value.scannedQuery)
        } else {
            getHistory(false, state.value.createdQuery)
        }
    }

    fun onEvent(event: HistoryEvent) {
        when (event) {
            is HistoryEvent.PageChanged -> {
                if (event.page == 0) {
                    getHistory(true, state.value.scannedQuery)
                } else {
                    getHistory(false, state.value.createdQuery)
                }
            }
            is HistoryEvent.QueryChanged -> getHistory(event.isScanned, event.query)
        }
    }

    private fun getHistory(isScanned: Boolean, query: String) {
        setLoading(isScanned, query)

        viewModelScope.coroutineScope.launch {
            historyDao.getHistory(isScanned, query).collectLatest { setSuccess(it) }
        }
    }

    private fun setLoading(isScanned: Boolean, query: String) {
        stateData.update {
            if (isScanned) {
                it.copy(
                    scannedQuery = query,
                    isScanned = isScanned,
                    isLoading = true
                )
            } else {
                it.copy(
                    createdQuery = query,
                    isScanned = isScanned,
                    isLoading = true
                )
            }
        }
    }

    private fun setSuccess(result: List<HistoryEntity>) {
        stateData.update {
            if (it.isScanned) {
                it.copy(
                    scannedHistory = result,
                    isLoading = false
                )
            } else {
                it.copy(
                    createdHistory = result,
                    isLoading = false
                )
            }
        }
    }
}