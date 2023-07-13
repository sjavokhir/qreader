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
            is BusinessContentEvent.Encoded -> onEncoded(event.value)
            is BusinessContentEvent.NameChanged -> onValueChanged(name = event.name)
            is BusinessContentEvent.IndustryChanged -> onValueChanged(industry = event.industry)
            is BusinessContentEvent.PhoneChanged -> onValueChanged(phone = event.phone)
            is BusinessContentEvent.EmailChanged -> onValueChanged(email = event.email)
            is BusinessContentEvent.WebsiteChanged -> onValueChanged(website = event.website)
            is BusinessContentEvent.AddressChanged -> onValueChanged(address = event.address)
        }
    }

    private fun onEncoded(value: String) {
        if (state.value.isSetEncoded) return

        val content = value.toBusinessContent() ?: return

        onValueChanged(
            name = content.name,
            industry = content.industry,
            phone = content.phone,
            email = content.email,
            website = content.website,
            address = content.address,
        )
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
                isEnabled = (mName.isNotEmpty() || mIndustry.isNotEmpty()) && mPhone.isNotEmpty(),
                isSetEncoded = true
            )
        }
    }
}