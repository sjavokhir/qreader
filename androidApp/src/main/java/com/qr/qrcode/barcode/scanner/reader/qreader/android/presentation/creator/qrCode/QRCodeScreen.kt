package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.qrCode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.helpers.ImageUtils
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.helpers.storagePermissions
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.CustomizeContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRFilledButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRImageContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QROutlinedButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.theme.LocalSubscription
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.CustomizeScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateMode
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.qrCode.QRCodeEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.qrCode.QRCodeViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.spec.Direction

@Destination
@Composable
fun QRCodeScreen(
    id: String,
    generateMode: GenerateMode,
    encoded: String,
    decoded: String,
    model: QRCustomizeModel,
    viewModel: QRCodeViewModel = viewModel(),
    navigator: DestinationsNavigator,
    resultCustomization: ResultRecipient<CustomizeScreenDestination, QRCustomizeModel>
) {
    var customizeModel by rememberSaveable { mutableStateOf(model) }

    resultCustomization.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {}
            is NavResult.Value -> {
                customizeModel = result.value
            }
        }
    }

    LaunchedEffect(id, encoded, customizeModel) {
        viewModel.onEvent(
            QRCodeEvent.InsertHistory(
                id = id,
                mode = generateMode,
                encoded = encoded,
                decoded = decoded,
                customize = model.toState()
            )
        )
    }

    QRBackground {
        QRCodeScreenContent(
            hasSubscription = LocalSubscription.current,
            encodedValue = encoded,
            model = customizeModel,
            onNavigate = navigator::navigate
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun QRCodeScreenContent(
    hasSubscription: Boolean,
    encodedValue: String,
    model: QRCustomizeModel,
    onNavigate: (Direction) -> Unit
) {
    val context = LocalContext.current
    val qrDrawable = rememberQRDrawable(
        content = encodedValue,
        model = model,
        ownLogo = ImageUtils.getDrawableFromPath(context, model.ownLogoPath)
    )

    val storagePermissionsState = rememberMultiplePermissionsState(storagePermissions)

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(20.dp)
    ) {
        item {
            CustomizeContent(
                model = model,
                hasSubscription = hasSubscription,
                onNavigate = onNavigate
            )
        }
        item {
            QRImageContent(qrDrawable)
        }
        item {
            ActionsContent(
                onSave = {
                    if (storagePermissionsState.allPermissionsGranted) {
                        ImageUtils.saveDrawableToGallery(
                            context = context,
                            drawable = qrDrawable
                        )
                    } else {
                        storagePermissionsState.launchMultiplePermissionRequest()
                    }
                },
                onShare = {
                    if (storagePermissionsState.allPermissionsGranted) {
                        ImageUtils.shareDrawable(
                            context = context,
                            drawable = qrDrawable
                        )
                    } else {
                        storagePermissionsState.launchMultiplePermissionRequest()
                    }
                }
            )
        }
    }
}

@Composable
private fun ActionsContent(
    onSave: () -> Unit,
    onShare: () -> Unit
) {
    val strings = LocalStrings.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        QROutlinedButton(
            text = strings.save,
            onClick = onSave,
            modifier = Modifier.weight(1f),
            leadingIcon = painterResource(id = R.drawable.ic_save)
        )

        QRFilledButton(
            text = strings.share,
            onClick = onShare,
            modifier = Modifier.weight(1f),
            leadingIcon = painterResource(id = R.drawable.ic_share)
        )
    }
}