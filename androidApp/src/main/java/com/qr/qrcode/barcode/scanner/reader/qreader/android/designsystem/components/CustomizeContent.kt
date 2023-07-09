package com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.clickableSingle
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.qrCode.QRCustomizeModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.CustomizeScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.PremiumScreenDestination
import com.ramcosta.composedestinations.spec.Direction

@Composable
fun CustomizeContent(
    model: QRCustomizeModel,
    hasSubscription: Boolean,
    onNavigate: (Direction) -> Unit
) {
    val strings = LocalStrings.current

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
                text = strings.customize,
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