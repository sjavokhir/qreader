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
            is ContactContentEvent.FirstNameChanged -> onValueChanged(firstName = event.firstName)
            is ContactContentEvent.LastNameChanged -> onValueChanged(lastName = event.lastName)
            is ContactContentEvent.PhoneChanged -> onValueChanged(phone = event.phone)
            is ContactContentEvent.EmailChanged -> onValueChanged(email = event.email)
            is ContactContentEvent.WebsiteChanged -> onValueChanged(website = event.website)
            is ContactContentEvent.AddressChanged -> onValueChanged(address = event.address)
        }
    }

    private fun onValueChanged(
        firstName: String? = null,
        lastName: String? = null,
        phone: String? = null,
        email: String? = null,
        website: String? = null,
        address: String? = null
    ) {
        stateData.update {
            val mFirstName = firstName ?: it.firstName
            val mPhone = phone ?: it.phone

            it.copy(
                firstName = mFirstName,
                lastName = lastName ?: it.lastName,
                phone = mPhone,
                email = email ?: it.email,
                website = website ?: it.website,
                address = address ?: it.address,
                isEnabled = mFirstName.isNotEmpty() && mPhone.isNotEmpty(),
                generateText = it.generateText()
            )
        }
    }

    private fun ContactContentState.generateText(): String {
        return buildString {
            append("BEGIN:VCARD\n")
            append("VERSION:3.0\n")
            append("N:").append(lastName).append(";").append(firstName).append(";;;").append("\n")
            append("FN:").append(firstName).append(" ").append(lastName).append("\n")
            append("TEL;TYPE=WORK,VOICE:").append(phone).append("\n")
            append("EMAIL;TYPE=PREF,INTERNET:").append(email).append("\n")
            append("URL:").append(website).append("\n")
            append("ADR;TYPE=WORK:").append(address).append(";").append("\n")
            append("END:VCARD")
        }
    }
}