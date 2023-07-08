package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.settings.permissions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.helpers.cameraPermission
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.helpers.locationPermissions
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.helpers.storagePermissions
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.DividerContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.localization.LocalStrings
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun ManagePermissionsScreen() {
    QRBackground {
        ManagePermissionsScreenContent()
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun ManagePermissionsScreenContent() {
    val strings = LocalStrings.current

    val cameraPermissionState = rememberPermissionState(cameraPermission)
    val locationPermissionState = rememberMultiplePermissionsState(locationPermissions)
    val storagePermissionState = rememberMultiplePermissionsState(storagePermissions)

    if (
        cameraPermissionState.status.isGranted &&
        locationPermissionState.allPermissionsGranted &&
        storagePermissionState.allPermissionsGranted
    ) {
        PermissionsGrantedContent()
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.05f)),
            contentAlignment = Alignment.TopStart
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                        shape = MaterialTheme.shapes.medium
                    )
                    .background(MaterialTheme.colorScheme.background)
            ) {
                if (!cameraPermissionState.status.isGranted) {
                    SwitchContent(
                        title = strings.allowCameraAccess,
                        description = strings.grantCameraPermission,
                        onCheckedChange = {
                            cameraPermissionState.launchPermissionRequest()
                        }
                    )
                }

                if (!locationPermissionState.allPermissionsGranted) {
                    DividerContent(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    SwitchContent(
                        title = strings.allowLocationAccess,
                        description = strings.grantLocationPermission,
                        onCheckedChange = {
                            locationPermissionState.launchMultiplePermissionRequest()
                        }
                    )
                }

                if (!storagePermissionState.allPermissionsGranted) {
                    DividerContent(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    SwitchContent(
                        title = strings.allowStorageAccess,
                        description = strings.grantStoragePermission,
                        onCheckedChange = {
                            storagePermissionState.launchMultiplePermissionRequest()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun PermissionsGrantedContent() {
    val strings = LocalStrings.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.ic_thank_you_illustration),
            contentDescription = null
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = strings.permissionsGranted,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )

            Text(
                text = strings.permissionsGrantedDescription,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun SwitchContent(
    title: String,
    description: String,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }

        Switch(
            checked = false,
            onCheckedChange = onCheckedChange,
            modifier = Modifier
                .width(39.dp)
                .height(24.dp)
                .scale(.85f)
        )
    }
}