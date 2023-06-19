package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.qrcode

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.clickableSingle
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.helpers.ImageUtils
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.helpers.storagePermissions
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRFilledButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRIcon
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QROutlinedButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.CustomizeScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.PremiumScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.spec.Direction

@Destination
@Composable
fun QRCodeScreen(
    generateText: String,
    navigator: DestinationsNavigator,
    resultCustomization: ResultRecipient<CustomizeScreenDestination, QRCustomizeModel>
) {
    var qrCustomizeModel by rememberSaveable { mutableStateOf(QRCustomizeModel()) }

    resultCustomization.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {}
            is NavResult.Value -> {
                qrCustomizeModel = result.value
            }
        }
    }

    QRBackground {
        QRCodeScreenContent(
            generateText = generateText,
            model = qrCustomizeModel,
            onNavigate = navigator::navigate
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun QRCodeScreenContent(
    generateText: String,
    model: QRCustomizeModel,
    onNavigate: (Direction) -> Unit
) {
    val context = LocalContext.current
    val qrDrawable = rememberQrDrawable(
        content = generateText,
        model = model
    )

    val storagePermissionsState = rememberMultiplePermissionsState(storagePermissions)

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(20.dp)
    ) {
        item {
            CustomizeContent(
                model = model,
                hasSubscription = true,
                onNavigate = onNavigate
            )
        }
        item {
            QrImageContent(qrDrawable)
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
private fun CustomizeContent(
    hasSubscription: Boolean,
    model: QRCustomizeModel,
    onNavigate: (Direction) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopEnd
    ) {
        Row(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    shape = MaterialTheme.shapes.medium
                )
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                .clickableSingle {
                    if (hasSubscription) {
                        onNavigate(CustomizeScreenDestination(model))
                    } else {
                        onNavigate(PremiumScreenDestination)
                    }
                }
                .padding(
                    horizontal = 14.dp,
                    vertical = 10.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            QRIcon(
                painter = painterResource(id = R.drawable.ic_customize),
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = stringResource(id = R.string.customize),
                style = MaterialTheme.typography.titleMedium,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )

            if (!hasSubscription) {
                Image(
                    painter = painterResource(id = R.drawable.ic_subscription),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun QrImageContent(qrDrawable: Drawable) {
    Image(
        painter = rememberDrawablePainter(drawable = qrDrawable),
        contentDescription = null,
        modifier = Modifier
            .aspectRatio(1f)
            .clip(MaterialTheme.shapes.medium)
    )
}

@Composable
private fun ActionsContent(
    onSave: () -> Unit,
    onShare: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        QROutlinedButton(
            text = stringResource(id = R.string.action_save),
            onClick = onSave,
            modifier = Modifier.weight(1f),
            leadingIcon = painterResource(id = R.drawable.ic_save)
        )

        QRFilledButton(
            text = stringResource(id = R.string.action_share),
            onClick = onShare,
            modifier = Modifier.weight(1f),
            leadingIcon = painterResource(id = R.drawable.ic_share)
        )
    }
}