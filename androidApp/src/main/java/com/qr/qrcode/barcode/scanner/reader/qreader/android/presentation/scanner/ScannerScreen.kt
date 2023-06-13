package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.scanner

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.GoProContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRIcon
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.DirectionDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.PremiumScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun ScannerScreen(
    navigator: DestinationsNavigator
) {
    QRBackground {
        ScannerScreenContent(
            onNavigate = navigator::navigate
        )
    }
}

@Composable
private fun ScannerScreenContent(
    onNavigate: (DirectionDestination) -> Unit
) {
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        GoProContent { onNavigate(PremiumScreenDestination) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(MaterialTheme.shapes.medium)
                .background(Color.Black)
        ) {
            CameraView(
                modifier = Modifier.fillMaxSize(),
                onResult = {}
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.align_qr_code),
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
                    onGalleryClick = {},
                    onFlashlightClick = {},
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
                .clickable(onClick = onGalleryClick),
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
                .clickable(onClick = onFlashlightClick),
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