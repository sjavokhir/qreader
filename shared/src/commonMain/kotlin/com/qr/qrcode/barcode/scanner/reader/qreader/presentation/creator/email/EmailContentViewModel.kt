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
            is EmailContentEvent.Encoded -> onEncoded(event.value)
            is EmailContentEvent.EmailChanged -> onValueChanged(email = event.email)
            is EmailContentEvent.SubjectChanged -> onValueChanged(subject = event.subject)
            is EmailContentEvent.MessageChanged -> onValueChanged(message = event.message)
        }
    }

    private fun onEncoded(value: String) {
        val content = value.toEmailContent() ?: return

        onValueChanged(
            email = content.email,
            subject = content.subject,
            message = content.message
        )
    }

    private fun onValueChanged(
        email: String? = null,
        subject: String? = null,
        message: String? = null
    ) {
        stateData.update {
            val mEmail = email ?: it.email

            it.copy(
                email = mEmail,
                subject = subject ?: it.subject,
                message = message ?: it.message,
                isEnabled = mEmail.isEmailValid(),
                isSetEncoded = true
            )
        }
    }
}