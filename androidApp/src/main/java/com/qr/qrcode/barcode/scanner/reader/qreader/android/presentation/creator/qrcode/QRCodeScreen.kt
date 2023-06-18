package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.qrcode

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.clickableSingle
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRFilledButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRIcon
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QROutlinedButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.CustomizeScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.PremiumScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction

@Destination
@Composable
fun QRCodeScreen(
    generateText: String = "text",
    navigator: DestinationsNavigator
) {
    QRBackground {
        QRCodeScreenContent(
            generateText = generateText,
            onNavigate = navigator::navigate
        )
    }
}

@Composable
private fun QRCodeScreenContent(
    generateText: String,
    onNavigate: (Direction) -> Unit
) {
    val imageBitmap = rememberQrImage(
        content = generateText,
        size = 512,
        padding = 2
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(20.dp)
    ) {
        item {
            CustomizeContent(
                hasSubscription = true,
                onNavigate = onNavigate
            )
        }
        item {
            QrImageContent(imageBitmap)
        }
        item {
            ActionsContent(
                onSave = {},
                onShare = {}
            )
        }
    }
}

@Composable
private fun CustomizeContent(
    hasSubscription: Boolean,
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
                        onNavigate(CustomizeScreenDestination)
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
private fun QrImageContent(imageBitmap: ImageBitmap) {
    Image(
        bitmap = imageBitmap,
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