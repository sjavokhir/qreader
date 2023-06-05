package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.premium

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.PriceType
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

class PremiumViewModel : BaseViewModel<PremiumState, PremiumEvent>(PremiumState()),
    KoinComponent {

    override fun onEvent(event: PremiumEvent) {
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