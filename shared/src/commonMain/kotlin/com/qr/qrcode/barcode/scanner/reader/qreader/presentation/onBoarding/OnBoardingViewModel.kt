package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.onBoarding

import com.qr.qrcode.barcode.scanner.reader.qreader.data.datastore.AppStore
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.EntryType
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.base.BaseViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class OnBoardingViewModel : BaseViewModel<OnBoardingState, OnBoardingEvent>(OnBoardingState()),
    KoinComponent {

    private val appStore by inject<AppStore>()

    override fun onEvent(event: OnBoardingEvent) {
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