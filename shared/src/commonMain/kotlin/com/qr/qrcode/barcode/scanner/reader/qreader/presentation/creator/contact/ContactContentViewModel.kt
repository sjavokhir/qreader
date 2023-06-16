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
            is ContactContentEvent.FirstNameChanged -> onValueChanged(firstName = event.value)
            is ContactContentEvent.LastNameChanged -> onValueChanged(lastName = event.value)
            is ContactContentEvent.PhoneChanged -> onValueChanged(phone = event.value)
            is ContactContentEvent.EmailChanged -> onValueChanged(email = event.value)
            is ContactContentEvent.WebsiteChanged -> onValueChanged(website = event.value)
            is ContactContentEvent.AddressChanged -> onValueChanged(address = event.value)
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
                isEnabled = mFirstName.isNotEmpty() && mPhone.isNotEmpty()
            )
        }
    }

//    fun getContent(): QrGenerateContent {
//        return QrGenerateContent(
//            qrContent = buildQrContent(),
//            formattedContent = buildFormattedContent()
//        )
//    }
//
//    private fun buildQrContent(): String {
//        return buildString {
//            append("BEGIN:VCARD\n")
//            append("VERSION:3.0\n")
//
//            // Name
//            append("N:")
//                .append(currentState.lastName).append(";")
//                .append(currentState.firstName).append(";;;\n")
//            append("FN:")
//                .append(currentState.firstName).append(" ")
//                .append(currentState.lastName).append("\n")
//
//            // Phone
//            append("TEL;TYPE=WORK,VOICE:").append(currentState.phone).append("\n")
//
//            // Email
//            append("EMAIL;TYPE=PREF,INTERNET:").append(currentState.email).append("\n")
//
//            // Website
//            append("URL:").append(currentState.website).append("\n")
//
//            // Address
//            append("ADR;TYPE=WORK:").append(currentState.address).append(";")
//
//            append("END:VCARD")
//        }
//    }
//
//    private fun buildFormattedContent(): String {
//        return buildString {
//            append("First Name: ${currentState.firstName}\n")
//            append("Last Name: ${currentState.lastName}\n")
//            append("Phone: ${currentState.phone}\n")
//            append("Email: ${currentState.email}\n")
//            append("Website: ${currentState.website}\n")
//            append("Address: ${currentState.address}")
//        }
//    }
}