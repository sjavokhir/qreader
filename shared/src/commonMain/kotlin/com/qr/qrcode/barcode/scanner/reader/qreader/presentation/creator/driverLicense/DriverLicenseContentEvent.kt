package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.driverLicense

sealed interface DriverLicenseContentEvent {
    data class DocumentTypeChanged(val documentType: String) : DriverLicenseContentEvent
    data class FirstNameChanged(val firstName: String) : DriverLicenseContentEvent
    data class MiddleNameChanged(val middleName: String) : DriverLicenseContentEvent
    data class LastNameChanged(val lastName: String) : DriverLicenseContentEvent
    data class GenderChanged(val gender: String) : DriverLicenseContentEvent
    data class StreetChanged(val street: String) : DriverLicenseContentEvent
    data class CityChanged(val city: String) : DriverLicenseContentEvent
    data class ZipChanged(val zip: String) : DriverLicenseContentEvent
    data class LicenseNumberChanged(val licenseNumber: String) : DriverLicenseContentEvent
    data class IssueDateChanged(val issueDate: String) : DriverLicenseContentEvent
    data class ExpiryDateChanged(val expiryDate: String) : DriverLicenseContentEvent
    data class BirthDateChanged(val birthDate: String) : DriverLicenseContentEvent
    data class IssuingCountryChanged(val issuingCountry: String) : DriverLicenseContentEvent
}
