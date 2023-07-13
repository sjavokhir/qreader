package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.clickableSingle
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.drawableId
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.DividerContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.theme.LocalSubscription
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.AddContentScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateHeader
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateMode
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.generateContents.GenerateContentsState
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.generateContents.GenerateContentsViewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.shared.randomUUID
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction

@Destination
@Composable
fun CreatorScreen(
    viewModel: GenerateContentsViewModel = viewModel(),
    navigator: DestinationsNavigator
) {
    val hasSubscription = LocalSubscription.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    QRBackground {
        GenerateContentsContent(
            hasSubscription = hasSubscription,
            state = state,
            onNavigate = navigator::navigate
        )
    }
}

@Composable
private fun GenerateContentsContent(
    hasSubscription: Boolean,
    state: GenerateContentsState,
    onNavigate: (Direction) -> Unit
) {
    val context = LocalContext.current

    Box {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(56.dp)
                    .clip(MaterialTheme.shapes.large)
                    .align(Alignment.Center),
                strokeCap = StrokeCap.Round
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopStart),
            ) {
                state.contents.forEach { content ->
                    item {
                        Text(
                            text = content.key.headerTitle(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .padding(top = 16.dp)
                                .padding(bottom = 8.dp)
                        )
                    }
                    itemsIndexed(content.value) { index, mode ->
                        GenerateContentItem(
                            context = context,
                            mode = mode,
                            hasSubscription = hasSubscription,
                            isLastItem = index == content.value.lastIndex,
                            onClick = {
                                onNavigate(
                                    AddContentScreenDestination(
                                        id = randomUUID(),
                                        generateMode = mode
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GenerateContentItem(
    context: Context,
    mode: GenerateMode,
    hasSubscription: Boolean,
    isLastItem: Boolean,
    onClick: () -> Unit
) {
    val strings = LocalStrings.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickableSingle(onClick = onClick)
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            context.drawableId(mode.icon)?.let { icon ->
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = mode.title,
                    modifier = Modifier
                        .size(42.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
            }

            Text(
                text = mode.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_chevron_right),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.outline)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!isLastItem) {
            DividerContent()
        }
    }
}

@Composable
private fun GenerateHeader.headerTitle(): String {
    val strings = LocalStrings.current

    return when (this) {
        GenerateHeader.Web -> strings.web
        GenerateHeader.Communication -> strings.communication
        GenerateHeader.Other -> strings.other
        GenerateHeader.SocialMedia -> strings.socialMedia
    }
}