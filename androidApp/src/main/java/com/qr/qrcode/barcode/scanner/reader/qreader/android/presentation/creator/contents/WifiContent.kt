package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRIcon
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRTextField
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.wifi.WifiContentEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.wifi.WifiContentState
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.wifi.WifiContentViewModel

@Composable
fun WifiContent(
    viewModel: WifiContentViewModel = viewModel(),
    encoded: String,
    onContent: (Boolean, String, String) -> Unit,
) {
    val strings = LocalStrings.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(state) {
        onContent(state.isEnabled, state.encode(), state.decode())
    }

    LaunchedEffect(encoded) {
        if (!state.isSetEncoded) {
            viewModel.onEvent(WifiContentEvent.Encoded(encoded))
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        QRTextField(
            value = state.networkName,
            onValueChange = {
                viewModel.onEvent(WifiContentEvent.NetworkNameChanged(it))
            },
            placeholder = strings.egPlaceholderWifiName,
            hint = strings.networkName
        )

        if (state.authentication != WifiContentState.Authentication.OPEN) {
            QRTextField(
                value = state.password,
                onValueChange = {
                    viewModel.onEvent(WifiContentEvent.PasswordChanged(it))
                },
                placeholder = strings.egPlaceholderWifiPassword,
                hint = strings.password,
            )
        }

        AuthenticationDropdown(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            hint = strings.encryptionType,
            selectedAuthentication = state.authentication.name,
            onSelectedAuthentication = {
                viewModel.onEvent(WifiContentEvent.SelectAuthentication(it))
                expanded = false
            },
            authentications = state.authentications
        )

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Switch(
                checked = state.isHidden,
                onCheckedChange = {
                    viewModel.onEvent(WifiContentEvent.HiddenChecked(it))
                },
                modifier = Modifier
                    .width(39.dp)
                    .height(24.dp)
                    .scale(.85f)
            )

            Text(
                text = strings.hidden,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AuthenticationDropdown(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    hint: String,
    selectedAuthentication: String,
    onSelectedAuthentication: (WifiContentState.Authentication) -> Unit,
    authentications: List<WifiContentState.Authentication>
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange
    ) {
        QRTextField(
            modifier = Modifier.menuAnchor(),
            value = selectedAuthentication,
            onValueChange = {},
            placeholder = hint,
            hint = hint,
            readOnly = true,
            trailingIcon = {
                QRIcon(
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    color = MaterialTheme.colorScheme.outline
                )
            }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            authentications.forEach { authentication ->
                Column {
                    DropdownMenuItem(
                        text = {
                            Column {
                                Text(
                                    text = authentication.name,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        },
                        onClick = { onSelectedAuthentication(authentication) },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .padding(ExposedDropdownMenuDefaults.ItemContentPadding),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                    )
                }
            }
        }
    }
}