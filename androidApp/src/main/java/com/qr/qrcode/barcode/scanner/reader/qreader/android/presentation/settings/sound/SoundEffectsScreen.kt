package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.settings.sound

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.clickableSingle
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.DividerContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRIcon
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.sound.SoundEffectsEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.sound.SoundEffectsState
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.sound.SoundEffectsViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun SoundEffectsScreen(
    viewModel: SoundEffectsViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    QRBackground {
        SoundEffectsScreenContent(
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
private fun SoundEffectsScreenContent(
    state: SoundEffectsState,
    onEvent: (SoundEffectsEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.05f)),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .clip(MaterialTheme.shapes.medium)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                    shape = MaterialTheme.shapes.medium
                )
                .background(MaterialTheme.colorScheme.background)
        ) {
            item {
                SwitchContent(
                    checked = state.isSoundEffectsChecked,
                    onCheckedChange = {
                        onEvent(SoundEffectsEvent.CheckSoundEffects(it))
                    }
                )
            }

            if (state.isSoundEffectsChecked) {
                item {
                    DividerContent(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                itemsIndexed(state.soundEffects) { index, sound ->
                    SoundItem(
                        sound = sound,
                        selectedSound = state.selectedSound,
                        hasDivider = index != state.soundEffects.lastIndex,
                        onClick = {
                            onEvent(SoundEffectsEvent.SelectSound(sound))
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SwitchContent(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    val strings = LocalStrings.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        QRIcon(
            painter = painterResource(id = R.drawable.ic_volume),
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = strings.soundEffects,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier
                .width(39.dp)
                .height(24.dp)
                .scale(.85f)
        )
    }
}

@Composable
private fun SoundItem(
    sound: Int,
    selectedSound: Int,
    hasDivider: Boolean,
    onClick: () -> Unit
) {
    val strings = LocalStrings.current

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
                text = "${strings.soundEffects} $sound",
                style = MaterialTheme.typography.bodyLarge,
                color = if (sound == selectedSound) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onBackground
                },
                fontWeight = if (sound == selectedSound) {
                    FontWeight.SemiBold
                } else {
                    FontWeight.Normal
                },
                modifier = Modifier.weight(1f)
            )

            QRIcon(
                painter = painterResource(id = R.drawable.ic_check_circle),
                color = if (sound == selectedSound) {
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