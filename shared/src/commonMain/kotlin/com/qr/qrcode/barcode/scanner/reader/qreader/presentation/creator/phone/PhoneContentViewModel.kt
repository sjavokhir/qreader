package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.phone

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PhoneContentViewModel : KMMViewModel() {

    private val stateData = MutableStateFlow(viewModelScope, PhoneContentState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    fun onEvent(event: PhoneContentEvent) {
        when (event) {
            is PhoneContentEvent.PhoneChanged -> onPhoneChanged(phone = event.phone)
        }
    }

    private fun onPhoneChanged(phone: String) {
        stateData.update {
            it.copy(
                phone = phone,
                isEnabled = phone.isNotEmpty()
            )
        }
    }

//    fun getContent(): QrGenerateContent {
//        return QrGenerateContent(
//            qrContent = (if (isWhatsApp) "https://wa.me/" else "tel:") + currentState.phone,
//            formattedContent = "${AppStrings.phoneNumber}: " + currentState.phone
//        )
//    }
}