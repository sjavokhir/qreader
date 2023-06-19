package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.customize

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRFilledButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.qrcode.QRCustomizeModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.qrcode.rememberQrDrawable
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.customize.CustomizeState
import com.qr.qrcode.barcode.scanner.reader.qreader.shared.appUrl

@Composable
fun QRPreviewDialog(
    ownLogo: Drawable?,
    model: QRCustomizeModel,
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties()
) {
    val qrDrawable = rememberQrDrawable(
        content = appUrl,
        model = model,
        ownLogo = ownLogo
    )

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.action_preview),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                QrImageContent(qrDrawable)

                QRFilledButton(
                    text = stringResource(id = R.string.action_ok),
                    onClick = onDismissRequest
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