package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRTextField
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.driverLicense.DriverLicenseContentEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.driverLicense.DriverLicenseContentViewModel

@Composable
fun DriverLicenseContent(
    viewModel: DriverLicenseContentViewModel = viewModel(),
    onContent: (Boolean) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        onContent(state.isEnabled)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        QRTextField(
            value = state.documentType,
            onValueChange = {
                viewModel.onEvent(DriverLicenseContentEvent.DocumentTypeChanged(it))
            },
            placeholder = stringResource(id = R.string.enter_value),
            hint = stringResource(id = R.string.document_type)
        )

        QRTextField(
            value = state.firstName,
            onValueChange = {
                viewModel.onEvent(DriverLicenseContentEvent.FirstNameChanged(it))
            },
            placeholder = stringResource(id = R.string.enter_value),
            hint = stringResource(id = R.string.first_name)
        )

        QRTextField(
            value = state.middleName,
            onValueChange = {
                viewModel.onEvent(DriverLicenseContentEvent.MiddleNameChanged(it))
            },
            placeholder = stringResource(id = R.string.enter_value),
            hint = stringResource(id = R.string.middle_name)
        )

        QRTextField(
            value = state.lastName,
            onValueChange = {
                viewModel.onEvent(DriverLicenseContentEvent.LastNameChanged(it))
            },
            placeholder = stringResource(id = R.string.enter_value),
            hint = stringResource(id = R.string.last_name)
        )

        QRTextField(
            value = state.gender,
            onValueChange = {
                viewModel.onEvent(DriverLicenseContentEvent.GenderChanged(it))
            },
            placeholder = stringResource(id = R.string.enter_value),
            hint = stringResource(id = R.string.gender)
        )

        QRTextField(
            value = state.street,
            onValueChange = {
                viewModel.onEvent(DriverLicenseContentEvent.StreetChanged(it))
            },
            placeholder = stringResource(id = R.string.enter_value),
            hint = stringResource(id = R.string.address_street)
        )

        QRTextField(
            value = state.city,
            onValueChange = {
                viewModel.onEvent(DriverLicenseContentEvent.CityChanged(it))
            },
            placeholder = stringResource(id = R.string.enter_value),
            hint = stringResource(id = R.string.address_city)
        )

        QRTextField(
            value = state.zip,
            onValueChange = {
                viewModel.onEvent(DriverLicenseContentEvent.ZipChanged(it))
            },
            placeholder = stringResource(id = R.string.enter_value),
            hint = stringResource(id = R.string.address_zip)
        )

        QRTextField(
            value = state.licenseNumber,
            onValueChange = {
                viewModel.onEvent(DriverLicenseContentEvent.LicenseNumberChanged(it))
            },
            placeholder = stringResource(id = R.string.enter_value),
            hint = stringResource(id = R.string.license_number)
        )

        QRTextField(
            value = state.issueDate,
            onValueChange = {
                viewModel.onEvent(DriverLicenseContentEvent.IssueDateChanged(it))
            },
            placeholder = stringResource(id = R.string.enter_value),
            hint = stringResource(id = R.string.issue_date)
        )

        QRTextField(
            value = state.expiryDate,
            onValueChange = {
                viewModel.onEvent(DriverLicenseContentEvent.ExpiryDateChanged(it))
            },
            placeholder = stringResource(id = R.string.enter_value),
            hint = stringResource(id = R.string.expiry_date)
        )

        QRTextField(
            value = state.birthDate,
            onValueChange = {
                viewModel.onEvent(DriverLicenseContentEvent.BirthDateChanged(it))
            },
            placeholder = stringResource(id = R.string.enter_value),
            hint = stringResource(id = R.string.birth_date)
        )

        QRTextField(
            value = state.issuingCountry,
            onValueChange = {
                viewModel.onEvent(DriverLicenseContentEvent.IssuingCountryChanged(it))
            },
            placeholder = stringResource(id = R.string.enter_value),
            hint = stringResource(id = R.string.issuing_country)
        )
    }
}