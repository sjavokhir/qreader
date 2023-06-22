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
            is BizContentEvent.FirstNameChanged -> onValueChanged(firstName = event.firstName)
            is BizContentEvent.LastNameChanged -> onValueChanged(lastName = event.lastName)
            is BizContentEvent.JobChanged -> onValueChanged(job = event.job)
            is BizContentEvent.CompanyChanged -> onValueChanged(company = event.company)
            is BizContentEvent.PhoneChanged -> onValueChanged(phone = event.phone)
            is BizContentEvent.EmailChanged -> onValueChanged(email = event.email)
            is BizContentEvent.AddressChanged -> onValueChanged(address = event.address)
        }
    }

    private fun onValueChanged(
        firstName: String? = null,
        lastName: String? = null,
        job: String? = null,
        company: String? = null,
        phone: String? = null,
        email: String? = null,
        address: String? = null
    ) {
        stateData.update {
            val mFirstName = firstName ?: it.firstName
            val mLastName = lastName ?: it.lastName
            val mJob = job ?: it.job
            val mPhone = phone ?: it.phone
            val mEmail = email ?: it.email

            it.copy(
                firstName = mFirstName,
                lastName = mLastName,
                job = mJob,
                company = company ?: it.company,
                phone = mPhone,
                email = mEmail,
                address = address ?: it.address,
                isEnabled = (mFirstName.isNotEmpty() || mLastName.isNotEmpty()) &&
                        mJob.isNotEmpty() &&
                        mPhone.isNotEmpty() &&
                        mEmail.isNotEmpty()
            )
        }
        stateData.update { it.copy(generateText = it.generateText()) }
    }

    private fun BizContentState.generateText(): String {
        return buildString {
            append("BEGIN:VCARD\n")
            append("VERSION:3.0\n")
            append("N:$firstName\n")
            append("ORG:$job\n")
            append("TEL:$phone\n")
            append("URL:$company\n")
            append("EMAIL:$email\n")
            append("ADR:$address\n")
            append("END:VCARD")
        }
    }
}