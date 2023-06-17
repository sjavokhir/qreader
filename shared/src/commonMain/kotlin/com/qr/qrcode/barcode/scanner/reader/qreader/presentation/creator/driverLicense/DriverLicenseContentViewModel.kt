package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.driverLicense

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DriverLicenseContentViewModel : KMMViewModel() {

    private val stateData = MutableStateFlow(viewModelScope, DriverLicenseContentState())

    @NativeCoroutinesState
    val state = stateData.asStateFlow()

    fun onEvent(event: DriverLicenseContentEvent) {
        when (event) {
            is DriverLicenseContentEvent.DocumentTypeChanged -> onValueChanged(documentType = event.documentType)
            is DriverLicenseContentEvent.FirstNameChanged -> onValueChanged(firstName = event.firstName)
            is DriverLicenseContentEvent.MiddleNameChanged -> onValueChanged(middleName = event.middleName)
            is DriverLicenseContentEvent.LastNameChanged -> onValueChanged(lastName = event.lastName)
            is DriverLicenseContentEvent.GenderChanged -> onValueChanged(gender = event.gender)
            is DriverLicenseContentEvent.StreetChanged -> onValueChanged(addressStreet = event.street)
            is DriverLicenseContentEvent.CityChanged -> onValueChanged(addressCity = event.city)
            is DriverLicenseContentEvent.ZipChanged -> onValueChanged(addressZip = event.zip)
            is DriverLicenseContentEvent.LicenseNumberChanged -> onValueChanged(licenseNumber = event.licenseNumber)
            is DriverLicenseContentEvent.IssueDateChanged -> onValueChanged(issueDate = event.issueDate)
            is DriverLicenseContentEvent.ExpiryDateChanged -> onValueChanged(expiryDate = event.expiryDate)
            is DriverLicenseContentEvent.BirthDateChanged -> onValueChanged(birthDate = event.birthDate)
            is DriverLicenseContentEvent.IssuingCountryChanged -> onValueChanged(issuingCountry = event.issuingCountry)
        }
    }

    private fun onValueChanged(
        documentType: String? = null,
        firstName: String? = null,
        middleName: String? = null,
        lastName: String? = null,
        gender: String? = null,
        addressStreet: String? = null,
        addressCity: String? = null,
        addressZip: String? = null,
        licenseNumber: String? = null,
        issueDate: String? = null,
        expiryDate: String? = null,
        birthDate: String? = null,
        issuingCountry: String? = null
    ) {
        stateData.update {
            val mDocumentType = documentType ?: it.documentType
            val mFirstName = firstName ?: it.firstName
            val mMiddleName = middleName ?: it.middleName
            val mLastName = lastName ?: it.lastName
            val mGender = gender ?: it.gender
            val mAddressStreet = addressStreet ?: it.street
            val mAddressCity = addressCity ?: it.city
            val mAddressZip = addressZip ?: it.zip
            val mLicenseNumber = licenseNumber ?: it.licenseNumber
            val mIssueDate = issueDate ?: it.issueDate
            val mExpiryDate = expiryDate ?: it.expiryDate
            val mBirthDate = birthDate ?: it.birthDate
            val mIssuingCountry = issuingCountry ?: it.issuingCountry

            it.copy(
                documentType = mDocumentType,
                firstName = mFirstName,
                middleName = mMiddleName,
                lastName = mLastName,
                gender = mGender,
                street = mAddressStreet,
                city = mAddressCity,
                zip = mAddressZip,
                licenseNumber = mLicenseNumber,
                issueDate = mIssueDate,
                expiryDate = mExpiryDate,
                birthDate = mBirthDate,
                issuingCountry = mIssuingCountry,
                isEnabled = mDocumentType.isNotEmpty() && mFirstName.isNotEmpty() &&
                        mMiddleName.isNotEmpty() && mLastName.isNotEmpty() &&
                        mGender.isNotEmpty() && mAddressStreet.isNotEmpty() &&
                        mAddressCity.isNotEmpty() && mAddressZip.isNotEmpty() &&
                        mLicenseNumber.isNotEmpty() && mIssueDate.isNotEmpty() &&
                        mExpiryDate.isNotEmpty() && mBirthDate.isNotEmpty() &&
                        mIssuingCountry.isNotEmpty(),
                generateText = it.generateText()
            )
        }
    }

    private fun DriverLicenseContentState.generateText(): String {
        return buildString {
            append("Document Type: $documentType\n")
            append("First Name: $firstName\n")
            append("Middle Name: $middleName\n")
            append("Last Name: $lastName\n")
            append("Gender: $gender\n")
            append("Address Street: $street\n")
            append("Address City: $city\n")
            append("Address Zip: ${zip}\n")
            append("License Number: $licenseNumber\n")
            append("Issue Date: $issueDate\n")
            append("Expiry Date: $expiryDate\n")
            append("Birth Date: $birthDate\n")
            append("Issuing Country: $issuingCountry}")
        }
    }
}