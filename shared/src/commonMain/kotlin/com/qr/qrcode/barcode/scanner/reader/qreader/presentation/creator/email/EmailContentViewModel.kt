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
            is EmailContentEvent.EmailChanged -> onEmailChanged(email = event.email)
        }
    }

    private fun onEmailChanged(email: String) {
        stateData.update {
            it.copy(
                email = email,
                isEnabled = email.isEmailValid()
            )
        }
    }

//    fun getContent(): QrGenerateContent {
//        return QrGenerateContent(
//            qrContent = "mailto:" + currentState.email,
//            formattedContent = "${AppStrings.email}: " + currentState.email
//        )
//    }
}