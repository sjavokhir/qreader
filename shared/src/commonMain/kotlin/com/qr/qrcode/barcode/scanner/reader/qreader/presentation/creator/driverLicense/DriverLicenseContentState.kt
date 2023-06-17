package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.driverLicense

data class DriverLicenseContentState(
    val documentType: String = "",
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val gender: String = "",
    val street: String = "",
    val city: String = "",
    val zip: String = "",
    val licenseNumber: String = "",
    val issueDate: String = "",
    val expiryDate: String = "",
    val birthDate: String = "",
    val issuingCountry: String = "",
    val isEnabled: Boolean = false
)