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
        }
    }

    private fun onValueChanged(
        message: String? = null, phone: String? = null
    ) {
        stateData.update {
            val mMessage = message ?: it.message
            val mPhone = phone ?: it.phone

            it.copy(
                message = mMessage,
                phone = mPhone,
                isEnabled = mMessage.isNotEmpty() && mPhone.isNotEmpty()
            )
        }
    }

//    fun getContent(): QrGenerateContent {
//        return QrGenerateContent(
//            qrContent = "sms:" + currentState.phoneNumber + "?body=" + currentState.message,
//            formattedContent = """
//                ${AppStrings.message}: ${currentState.message}
//                ${AppStrings.phoneNumber}: ${currentState.phoneNumber}
//            """.trimIndent()
//        )
//    }
}