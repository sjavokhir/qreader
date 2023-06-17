package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.premium

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.PriceType
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

class PremiumViewModel : KMMViewModel(), KoinComponent {

    private val stateData = MutableStateFlow(viewModelScope, PremiumState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    fun onEvent(event: PremiumEvent) {
        when (event) {
            is PremiumEvent.SelectPrice -> setSelectPrice(event.price)
        }
    }

    private fun setSelectPrice(price: PriceType) {
        stateData.update {
            it.copy(selectedPrice = price)
        }
    }
}