package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.sound

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRIcon
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
                item { DividerContent() }

                items(state.soundEffects) { sound ->
                    Column {
                        SoundItem(
                            sound = sound,
                            selectedSound = state.selectedSound,
                            onClick = {
                                onEvent(SoundEffectsEvent.SelectSound(sound))
                            }
                        )

                        DividerContent()
                    }
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
            text = stringResource(id = R.string.sound_effects),
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
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "${stringResource(id = R.string.sound_effects)} $sound",
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

        if (sound == selectedSound) {
            QRIcon(
                painter = painterResource(id = R.drawable.ic_check_circle),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun DividerContent() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
    )
}