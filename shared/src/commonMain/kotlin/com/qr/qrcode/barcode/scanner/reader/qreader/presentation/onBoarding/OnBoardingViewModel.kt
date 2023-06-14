package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.onBoarding

import com.qr.qrcode.barcode.scanner.reader.qreader.data.datastore.AppStore
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.EntryType
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class OnBoardingViewModel : KMMViewModel(), KoinComponent {

    private val appStore by inject<AppStore>()

    private val stateData = MutableStateFlow(viewModelScope, OnBoardingState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    fun onEvent(event: OnBoardingEvent) {
        when (event) {
            OnBoardingEvent.Start -> onStart()
            OnBoardingEvent.Idle -> setIdle()
        }
    }

    private fun onStart() {
        viewModelScope.coroutineScope.launch {
            appStore.setEntryType(EntryType.Scanner)

            stateData.update { it.copy(isStart = true) }
        }
    }

    private fun setIdle() {
        stateData.update { it.copy(isStart = false) }
    }
}