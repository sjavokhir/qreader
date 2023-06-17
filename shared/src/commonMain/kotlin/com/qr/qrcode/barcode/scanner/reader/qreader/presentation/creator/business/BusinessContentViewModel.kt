package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.business

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BusinessContentViewModel : KMMViewModel() {

    private val stateData = MutableStateFlow(viewModelScope, BusinessContentState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    fun onEvent(event: BusinessContentEvent) {
        when (event) {
            is BusinessContentEvent.NameChanged -> onValueChanged(name = event.name)
            is BusinessContentEvent.IndustryChanged -> onValueChanged(industry = event.industry)
            is BusinessContentEvent.PhoneChanged -> onValueChanged(phone = event.phone)
            is BusinessContentEvent.EmailChanged -> onValueChanged(email = event.email)
            is BusinessContentEvent.WebsiteChanged -> onValueChanged(website = event.website)
            is BusinessContentEvent.AddressChanged -> onValueChanged(address = event.address)
        }
    }

    private fun onValueChanged(
        name: String? = null,
        industry: String? = null,
        phone: String? = null,
        email: String? = null,
        website: String? = null,
        address: String? = null
    ) {
        stateData.update {
            val mName = name ?: it.name
            val mIndustry = industry ?: it.industry
            val mPhone = phone ?: it.phone

            it.copy(
                name = mName,
                industry = mIndustry,
                phone = mPhone,
                email = email ?: it.email,
                website = website ?: it.website,
                address = address ?: it.address,
                isEnabled = (mName.isNotEmpty() || mIndustry.isNotEmpty()) &&
                        mPhone.isNotEmpty()
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
//            // Organization
//            append("ORG:").append(currentState.name).append("\n")
//            append("INDUSTRY:").append(currentState.industry).append("\n")
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
//            append("ADR;TYPE=WORK:").append(currentState.address).append("\n")
//
//            append("END:VCARD")
//        }
//    }
//
//    private fun buildFormattedContent(): String {
//        return buildString {
//            append("Company Name: ${currentState.name}\n")
//            append("Industry: ${currentState.industry}\n")
//            append("Phone: ${currentState.phone}\n")
//            append("Email: ${currentState.email}\n")
//            append("Website: ${currentState.website}\n")
//            append("Address: ${currentState.address}")
//        }
//    }
}