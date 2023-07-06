package com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.BottomAppBarDefaults.windowInsets
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.clickableSingle
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.premium.Subscriptions

@Composable
fun QRNavigationBar(
    modifier: Modifier = Modifier,
    hasSubscription: Boolean,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
    ) {
        Column {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp),
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
            )

            BannerAdView()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(windowInsets)
                    .height(65.dp)
                    .selectableGroup(),
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    }
}

@Composable
fun RowScope.QRNavigationBarItem(
    modifier: Modifier = Modifier,
    icon: Painter,
    label: String,
    onClick: () -> Unit,
    selected: Boolean
) {
    Column(
        modifier = modifier
            .weight(1f)
            .clickableSingle(
                onClick = onClick,
                hasIndication = false
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        QRIcon(
            painter = icon,
            color = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            }
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (selected) {
                FontWeight.SemiBold
            } else {
                FontWeight.Normal
            },
            color = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            }
        )
    }
}