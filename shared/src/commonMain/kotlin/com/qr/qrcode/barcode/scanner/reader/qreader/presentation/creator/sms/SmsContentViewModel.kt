package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.sms

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

class SmsContentViewModel : KMMViewModel(), KoinComponent {

    private val stateData = MutableStateFlow(viewModelScope, SmsContentState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    fun onEvent(event: SmsContentEvent) {
        when (event) {
            is SmsContentEvent.MessageChanged -> onValueChanged(message = event.message)
            is SmsContentEvent.PhoneChanged -> onValueChanged(phone = event.phone)
            is SmsContentEvent.UseMMSChecked -> onValueChanged(useMMS = event.isChecked)
        }
    }

    private fun onValueChanged(
        phone: String? = null,
        message: String? = null,
        useMMS: Boolean? = null
    ) {
        stateData.update {
            val mPhone = phone ?: it.phone
            val mMessage = message ?: it.message

            it.copy(
                phone = mPhone,
                message = mMessage,
                useMMS = useMMS ?: it.useMMS,
                isEnabled = mMessage.isNotEmpty() && mPhone.isNotEmpty()
            )
        }
    }
}