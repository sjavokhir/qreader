package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.customize

import android.graphics.drawable.Drawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRFilledButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRImageContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.model.QRCustomizeModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.rememberQRDrawable
import com.qr.qrcode.barcode.scanner.reader.qreader.shared.appUrl

@Composable
fun QRPreviewDialog(
    ownLogo: Drawable?,
    customize: QRCustomizeModel,
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties()
) {
    val strings = LocalStrings.current

    val qrDrawable = rememberQRDrawable(
        content = appUrl,
        customize = customize,
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
                    text = strings.preview,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                QRImageContent(qrDrawable)

                QRFilledButton(
                    text = strings.ok,
                    onClick = onDismissRequest
                )
            }
        }
    }
}