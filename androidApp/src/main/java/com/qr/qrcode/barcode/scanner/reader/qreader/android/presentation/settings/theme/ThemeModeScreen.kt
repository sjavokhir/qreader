package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.settings.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.clickableSingle
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.DividerContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRIcon
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.ThemeMode
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.theme.ThemeModeEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.theme.ThemeModeState
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.theme.ThemeModeViewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.shared.Event
import com.qr.qrcode.barcode.scanner.reader.qreader.shared.EventChannel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun ThemeModeScreen(
    viewModel: ThemeModeViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    QRBackground {
        ThemeModeScreenContent(
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
private fun ThemeModeScreenContent(
    state: ThemeModeState,
    onEvent: (ThemeModeEvent) -> Unit
) {
    val strings = LocalStrings.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.05f)),
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(20.dp)
                .clip(MaterialTheme.shapes.medium)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                    shape = MaterialTheme.shapes.medium
                )
                .background(MaterialTheme.colorScheme.background)
        ) {
            itemsIndexed(state.themeModes) { index, mode ->
                ThemeModeItem(
                    title = when (mode) {
                        ThemeMode.System -> strings.systemDefault
                        ThemeMode.Light -> strings.light
                        ThemeMode.Dark -> strings.dark
                    },
                    themeMode = mode,
                    selectedThemeMode = state.selectedTheme,
                    hasDivider = index != state.themeModes.lastIndex,
                    onClick = {
                        onEvent(ThemeModeEvent.SelectThemeMode(mode))

                        EventChannel.sendEvent(Event.ThemeModeChanged(mode))
                    }
                )
            }
        }
    }
}

@Composable
private fun ThemeModeItem(
    title: String,
    themeMode: ThemeMode,
    selectedThemeMode: ThemeMode,
    hasDivider: Boolean,
    onClick: () -> Unit
) {
    val isSelected = remember(selectedThemeMode) { themeMode == selectedThemeMode }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickableSingle(onClick = onClick)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onBackground
                },
                fontWeight = if (isSelected) {
                    FontWeight.SemiBold
                } else {
                    FontWeight.Normal
                },
                modifier = Modifier.weight(1f)
            )

            QRIcon(
                painter = painterResource(id = R.drawable.ic_check_circle),
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Transparent
                }
            )
        }

        if (hasDivider) {
            DividerContent(
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}