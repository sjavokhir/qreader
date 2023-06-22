package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.contact

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ContactContentViewModel : KMMViewModel() {

    private val stateData = MutableStateFlow(viewModelScope, ContactContentState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    fun onEvent(event: ContactContentEvent) {
        when (event) {
            is ContactContentEvent.NameChanged -> onValueChanged(name = event.name)
            is ContactContentEvent.PhoneChanged -> onValueChanged(phone = event.phone)
            is ContactContentEvent.EmailChanged -> onValueChanged(email = event.email)
            is ContactContentEvent.AddressChanged -> onValueChanged(address = event.address)
        }
    }

    private fun onValueChanged(
        name: String? = null,
        phone: String? = null,
        email: String? = null,
        address: String? = null
    ) {
        stateData.update {
            val mName = name ?: it.name
            val mPhone = phone ?: it.phone

            it.copy(
                name = mName,
                phone = mPhone,
                email = email ?: it.email,
                address = address ?: it.address,
                isEnabled = mName.isNotEmpty() && mPhone.isNotEmpty()
            )
        }
        stateData.update { it.copy(generateText = it.generateText()) }
    }

    private fun ContactContentState.generateText(): String {
        return buildString {
            append("MECARD:")
            append("N:$name;")
            append("ADR:$address;")
            append("TEL:$phone;")
            append("EMAIL:$email;")
            append(";")
        }
    }
}