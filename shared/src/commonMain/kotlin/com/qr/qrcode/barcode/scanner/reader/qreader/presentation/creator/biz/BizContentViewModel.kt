package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.biz

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BizContentViewModel : KMMViewModel() {

    private val stateData = MutableStateFlow(viewModelScope, BizContentState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    fun onEvent(event: BizContentEvent) {
        when (event) {
            is BizContentEvent.Encoded -> onEncoded(event.value)
            is BizContentEvent.FirstNameChanged -> onValueChanged(firstName = event.firstName)
            is BizContentEvent.LastNameChanged -> onValueChanged(lastName = event.lastName)
            is BizContentEvent.JobChanged -> onValueChanged(job = event.job)
            is BizContentEvent.CompanyChanged -> onValueChanged(company = event.company)
            is BizContentEvent.PhoneChanged -> onValueChanged(phone = event.phone)
            is BizContentEvent.EmailChanged -> onValueChanged(email = event.email)
            is BizContentEvent.WebsiteChanged -> onValueChanged(website = event.website)
            is BizContentEvent.AddressChanged -> onValueChanged(address = event.address)
        }
    }

    private fun onEncoded(value: String) {
        val content = value.toBizContent() ?: return

        onValueChanged(
            firstName = content.firstName,
            lastName = content.lastName,
            job = content.job,
            company = content.company,
            phone = content.phone,
            email = content.email,
            website = content.website,
            address = content.address,
        )
    }

    private fun onValueChanged(
        firstName: String? = null,
        lastName: String? = null,
        job: String? = null,
        company: String? = null,
        phone: String? = null,
        email: String? = null,
        website: String? = null,
        address: String? = null
    ) {
        stateData.update {
            val mFirstName = firstName ?: it.firstName
            val mLastName = lastName ?: it.lastName
            val mPhone = phone ?: it.phone

            it.copy(
                firstName = mFirstName,
                lastName = mLastName,
                company = company ?: it.company,
                job = job ?: it.job,
                phone = mPhone,
                email = email ?: it.email,
                website = website ?: it.website,
                address = address ?: it.address,
                isEnabled = (mFirstName.isNotEmpty() || mLastName.isNotEmpty()) && mPhone.isNotEmpty()
            )
        }
    }
}