package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.settings.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.HyperlinkText
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRFilledButton
import com.qr.qrcode.barcode.scanner.reader.qreader.core.helpers.Constants
import com.qr.qrcode.barcode.scanner.reader.qreader.shared.appVersion
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun AboutScreen() {
    QRBackground {
        AboutScreenContent()
    }
}

@Composable
private fun AboutScreenContent() {
    Column(
        modifier = Modifier
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineMedium
        )

        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier.size(160.dp),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary)
        )

        Text(
            text = "v${appVersion}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.outline
        )

        QRFilledButton(
            text = stringResource(id = R.string.update_new_version),
            onClick = {}
        )

        Spacer(modifier = Modifier.weight(1f))

        HyperlinkText(
            fullText = stringResource(id = R.string.you_agree_terms),
            linkText = listOf(
                stringResource(id = R.string.terms_of_use)
            ),
            hyperlinks = listOf(Constants.TERMS_URL),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.outline
            )
        )
    }
}