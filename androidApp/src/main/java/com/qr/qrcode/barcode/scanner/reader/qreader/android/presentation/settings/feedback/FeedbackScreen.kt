package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.settings.feedback

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.sendMail
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRFilledButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.core.helpers.Constants
import com.qr.qrcode.barcode.scanner.reader.qreader.shared.appVersion
import com.qr.qrcode.barcode.scanner.reader.qreader.shared.deviceVersion
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun FeedbackScreen() {
    val context = LocalContext.current
    val strings = LocalStrings.current

    QRBackground {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(20.dp)
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.ic_feedback_illustration),
                    contentDescription = null
                )
            }
            item {
                Text(
                    text = strings.helpUsImprove,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }
            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = strings.feedbackDescription,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outline,
                    textAlign = TextAlign.Start
                )
            }
            item {
                QRFilledButton(
                    text = strings.sendUs,
                    onClick = {
                        context.sendMail(
                            email = Constants.EMAIL,
                            subject = "Feedback regarding ${strings.appName} [$appVersion - $deviceVersion]"
                        )
                    }
                )
            }
        }
    }
}