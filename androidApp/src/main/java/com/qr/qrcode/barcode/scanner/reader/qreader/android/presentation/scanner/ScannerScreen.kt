package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.scanner

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.clickableSingle
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.model.QRCustomizeModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.CameraView
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.GoProContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRIcon
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.theme.LocalSubscription
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.ImageCropperScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.PremiumScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.QRCodeScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.core.datetime.defaultDateTime
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.scanner.ScannerState
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.scanner.ScannerViewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.shared.randomUUID
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction

@RootNavGraph(start = true)
@Destination
@Composable
fun ScannerScreen(
    viewModel: ScannerViewModel = viewModel(),
    navigator: DestinationsNavigator
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val hasSubscription = LocalSubscription.current

    QRBackground {
        ScannerScreenContent(
            state = state,
            hasSubscription = hasSubscription,
            onNavigate = navigator::navigate
        )
    }
}

@Composable
private fun ScannerScreenContent(
    state: ScannerState,
    hasSubscription: Boolean,
    onNavigate: (Direction) -> Unit
) {
    val strings = LocalStrings.current

    var isFlashlightOn by remember { mutableStateOf(false) }

    val photoPicker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        val uri = uris.firstOrNull() ?: return@rememberLauncherForActivityResult

        onNavigate(
            ImageCropperScreenDestination(
                imageUri = uri.toString(),
                isVibrateEnabled = state.isVibrateEnabled,
                isOpenWebPagesEnabled = state.isOpenWebPagesEnabled,
                isChromeCustomTabsEnabled = state.isChromeCustomTabsEnabled
            )
        )
    }

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        if (!hasSubscription) {
            GoProContent { onNavigate(PremiumScreenDestination) }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(MaterialTheme.shapes.medium)
                .background(Color.Black)
        ) {
            CameraView(
                modifier = Modifier.fillMaxSize(),
                isFlashlightOn = isFlashlightOn,
                isVibrateEnabled = state.isVibrateEnabled,
                isOpenWebPagesEnabled = state.isOpenWebPagesEnabled,
                isChromeCustomTabsEnabled = state.isChromeCustomTabsEnabled
            ) { encoded, decoded, mode ->
                onNavigate(
                    QRCodeScreenDestination(
                        id = randomUUID(),
                        dateTime = defaultDateTime(),
                        isScanned = true,
                        generateMode = mode,
                        encoded = encoded,
                        decoded = decoded,
                        customize = QRCustomizeModel(),
                        isEditable = false,
                        isDeletable = false
                    )
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = strings.alignQrCode,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_scanner_lines),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 32.dp)
                )

                ScannerActionsContent(
                    onGalleryClick = {
                        photoPicker.launch("image/*")
                    },
                    onFlashlightClick = {
                        isFlashlightOn = !isFlashlightOn
                    },
                )
            }
        }
    }
}

@Composable
private fun ScannerActionsContent(
    onGalleryClick: () -> Unit,
    onFlashlightClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 20.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(MaterialTheme.shapes.medium)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.medium
                )
                .background(Color.Black.copy(alpha = 0.1f))
                .clickableSingle(onClick = onGalleryClick),
            contentAlignment = Alignment.Center
        ) {
            QRIcon(
                painter = painterResource(id = R.drawable.ic_gallery),
                color = Color.White
            )
        }

        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(MaterialTheme.shapes.medium)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.medium
                )
                .background(Color.Black.copy(alpha = 0.25f))
                .clickableSingle(onClick = onFlashlightClick),
            contentAlignment = Alignment.Center
        ) {
            QRIcon(
                painter = painterResource(id = R.drawable.ic_flashlight),
                color = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}