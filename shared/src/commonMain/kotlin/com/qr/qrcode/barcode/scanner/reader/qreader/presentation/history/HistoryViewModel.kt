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

    fun onEvent(event: HistoryEvent) {
        when (event) {
            is HistoryEvent.QueryChanged -> onQueryChanged(event.query)
            is HistoryEvent.GetHistory -> getHistory(event.page)
        }
    }

    private fun onQueryChanged(query: String) {
        stateData.update { it.copy(query = query) }
    }

    private fun getHistory(page: Int) {
        val isScanned = page == 1

        setLoading(isScanned)

        viewModelScope.coroutineScope.launch {
            historyDao.getHistory(isScanned, state.value.query).collectLatest {
                setSuccess(isScanned, it)
            }
        }
    }

    private fun setLoading(isScanned: Boolean) {
        stateData.update {
            it.copy(
                isScanned = isScanned,
                isLoading = true
            )
        }
    }

    private fun setSuccess(
        isScanned: Boolean,
        result: List<HistoryEntity>
    ) {
        stateData.update {
            if (isScanned) {
                it.copy(
                    scannedHistory = result,
                    isScanned = isScanned,
                    isLoading = false
                )
            } else {
                it.copy(
                    createdHistory = result,
                    isScanned = isScanned,
                    isLoading = false
                )
            }
        }
    }
}