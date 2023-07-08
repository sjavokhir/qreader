package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.premium

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.clickableSingle
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.getActivity
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.restartApp
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.HyperlinkText
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRFilledButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRIcon
import com.qr.qrcode.barcode.scanner.reader.qreader.core.helpers.Constants
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun PremiumScreen(
    viewModel: PremiumViewModel = viewModel(),
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsStateWithLifecycle()
    val isPurchaseAcknowledged by viewModel.isPurchaseAcknowledged.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    LaunchedEffect(isPurchaseAcknowledged) {
        if (isPurchaseAcknowledged) {
            context.restartApp()
        }
    }

    QRBackground {
        if (isLoading) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(MaterialTheme.shapes.large),
                    strokeCap = StrokeCap.Round
                )
            }
        } else {
            PremiumScreenContent(
                state = state,
                onEvent = viewModel::onEvent,
                onNavigateUp = navigator::navigateUp
            )
        }
    }
}

@Composable
private fun PremiumScreenContent(
    state: PremiumState,
    onEvent: (PremiumEvent) -> Unit,
    onNavigateUp: () -> Unit
) {
    val context = LocalContext.current

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

        if (state.productDetails.isEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(MaterialTheme.shapes.large),
                        strokeCap = StrokeCap.Round
                    )
                }
            }
        } else {
            items(state.productDetails) { product ->
                PriceItem(
                    title = product.name,
                    description = product.description,
                    caption = product.productId,
                    price = product.formattedPrice,
                    enabled = product.productId == state.selectedProductId,
                    onClick = {
                        onEvent(PremiumEvent.SelectProduct(product.productId))
                    }
                )
            }
        }

        item {
            QRFilledButton(
                text = stringResource(id = R.string.action_continue),
                onClick = {
                    context.getActivity()?.let {
                        onEvent(PremiumEvent.Buy(it))
                    }
                }
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
private fun PriceItem(
    title: String,
    description: String,
    caption: String,
    price: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
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
            .padding(
                vertical = 12.dp,
                horizontal = 16.dp
            )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = price,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = caption.caption(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}