package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.premium

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.clickableSingle
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.HyperlinkText
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRFilledButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRIcon
import com.qr.qrcode.barcode.scanner.reader.qreader.core.helpers.Constants
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.PriceType
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.premium.PremiumEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.premium.PremiumState
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.premium.PremiumViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun PremiumScreen(
    viewModel: PremiumViewModel = viewModel(),
    navigator: DestinationsNavigator
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    QRBackground {
        PremiumScreenContent(
            state = state,
            onEvent = viewModel::onEvent,
            onNavigateUp = navigator::navigateUp
        )
    }
}

@Composable
private fun PremiumScreenContent(
    state: PremiumState,
    onEvent: (PremiumEvent) -> Unit,
    onNavigateUp: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(
            vertical = 32.dp,
            horizontal = 20.dp
        ),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item { HeaderContent(onNavigateUp) }
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.welcome_to_premium),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = stringResource(id = R.string.premium_description),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
        item { FeaturesContent() }
        item { PricesContent(state, onEvent) }
        item {
            QRFilledButton(
                text = stringResource(id = R.string.action_continue),
                onClick = {}
            )
        }
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.cancel_anytime),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline,
                textAlign = TextAlign.Center
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                HyperlinkText(
                    fullText = stringResource(id = R.string.terms),
                    linkText = listOf(stringResource(id = R.string.terms)),
                    hyperlinks = listOf(Constants.TERMS_URL),
                    style = MaterialTheme.typography.bodyLarge
                )

                HyperlinkText(
                    fullText = stringResource(id = R.string.privacy),
                    linkText = listOf(stringResource(id = R.string.privacy)),
                    hyperlinks = listOf(Constants.PRIVACY_URL),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
private fun HeaderContent(
    onNavigateUp: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_remove_ads),
            contentDescription = null
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .clickableSingle(onClick = onNavigateUp),
            contentAlignment = Alignment.Center
        ) {
            QRIcon(
                painter = painterResource(id = R.drawable.ic_cancel),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun FeaturesContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                shape = MaterialTheme.shapes.medium
            )
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FeatureItem(feature = R.string.feature_1)
        FeatureItem(feature = R.string.feature_2)
        FeatureItem(feature = R.string.feature_3)
        FeatureItem(feature = R.string.feature_4)
    }
}

@Composable
private fun FeatureItem(feature: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        QRIcon(
            painter = painterResource(id = R.drawable.ic_check_outline),
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = stringResource(id = feature),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun PricesContent(
    state: PremiumState,
    onEvent: (PremiumEvent) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        PriceItem(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            title = stringResource(id = R.string.best_value),
            description = stringResource(id = R.string.lifetime),
            caption = stringResource(id = R.string.one_purchase),
            price = 59.99,
            enabled = state.selectedPrice == PriceType.Lifetime,
            onClick = {
                onEvent(PremiumEvent.SelectPrice(PriceType.Lifetime))
            }
        )

        PriceItem(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            title = stringResource(id = R.string.most_popular),
            description = stringResource(id = R.string.seven_days_free),
            caption = stringResource(id = R.string.one_month),
            price = 4.99,
            enabled = state.selectedPrice == PriceType.Month,
            onClick = {
                onEvent(PremiumEvent.SelectPrice(PriceType.Month))
            }
        )

        PriceItem(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            title = stringResource(id = R.string.save_percent),
            description = stringResource(id = R.string.seven_days_free),
            caption = stringResource(id = R.string.one_year),
            price = 29.99,
            enabled = state.selectedPrice == PriceType.Year,
            onClick = {
                onEvent(PremiumEvent.SelectPrice(PriceType.Year))
            }
        )
    }
}

@Composable
private fun PriceItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    caption: String,
    price: Double,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .border(
                width = if (enabled) (1.5).dp else 1.dp,
                color = if (enabled) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                },
                shape = MaterialTheme.shapes.medium
            )
            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.07f))
            .clickableSingle(onClick = onClick)
            .padding(12.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(top = 3.dp),
        )

        Row(
            modifier = Modifier.padding(top = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = "$",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )
            Text(
                text = price.toString(),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = caption,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(top = 3.dp),
        )
    }
}