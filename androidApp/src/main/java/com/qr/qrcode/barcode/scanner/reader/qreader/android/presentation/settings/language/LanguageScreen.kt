package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.settings.language

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRIcon
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.LanguageType
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.language.LanguageEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.language.LanguageState
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.language.LanguageViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun LanguageScreen(
    viewModel: LanguageViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    QRBackground {
        LanguageScreenContent(
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
private fun LanguageScreenContent(
    state: LanguageState,
    onEvent: (LanguageEvent) -> Unit
) {
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
            items(state.languages) { language ->
                Column {
                    LanguageItem(
                        language = language,
                        selectedLanguage = state.selectedLanguage,
                        onClick = {
                            onEvent(LanguageEvent.SelectLanguage(language))
                        }
                    )

                    DividerContent()
                }
            }
        }
    }
}

@Composable
private fun LanguageItem(
    language: LanguageType,
    selectedLanguage: LanguageType,
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
        Image(
            painter = painterResource(
                id = when (language) {
                    LanguageType.English -> R.drawable.ic_english
                    LanguageType.Uzbek -> R.drawable.ic_uzbekistan
                    LanguageType.Arabic -> R.drawable.ic_saudi_arabia
                    LanguageType.Turkish -> R.drawable.ic_turkey
                    LanguageType.German -> R.drawable.ic_germany
                    LanguageType.French -> R.drawable.ic_france
                    LanguageType.Japanese -> R.drawable.ic_japan
                    LanguageType.Korean -> R.drawable.ic_south_korea
                    LanguageType.Portuguese -> R.drawable.ic_portugal
                    LanguageType.Spanish -> R.drawable.ic_spain
                    LanguageType.Italian -> R.drawable.ic_italy
                    LanguageType.Russian -> R.drawable.ic_russia
                    LanguageType.Chinese -> R.drawable.ic_china
                }
            ),
            contentDescription = language.language,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = language.language,
            style = MaterialTheme.typography.bodyLarge,
            color = if (language == selectedLanguage) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onBackground
            },
            fontWeight = if (language == selectedLanguage) {
                FontWeight.SemiBold
            } else {
                FontWeight.Normal
            },
            modifier = Modifier.weight(1f)
        )

        if (language == selectedLanguage) {
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