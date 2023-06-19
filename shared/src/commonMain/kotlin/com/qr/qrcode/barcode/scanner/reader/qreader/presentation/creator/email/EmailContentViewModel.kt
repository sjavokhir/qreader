package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.email

import com.qr.qrcode.barcode.scanner.reader.qreader.core.extensions.isEmailValid
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EmailContentViewModel : KMMViewModel() {

    private val stateData = MutableStateFlow(viewModelScope, EmailContentState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    fun onEvent(event: EmailContentEvent) {
        when (event) {
            is EmailContentEvent.EmailChanged -> onValueChanged(email = event.email)
            is EmailContentEvent.MessageChanged -> onValueChanged(message = event.message)
        }
    }

    private fun onValueChanged(
        email: String? = null,
        message: String? = null
    ) {
        stateData.update {
            val mEmail = email ?: it.email

            it.copy(
                email = mEmail,
                message = message ?: it.message,
                isEnabled = mEmail.isEmailValid(),
                generateText = it.generateText()
            )
        }
    }

    private fun EmailContentState.generateText(): String {
        return "mailto:$email?body=$message"
    }
}