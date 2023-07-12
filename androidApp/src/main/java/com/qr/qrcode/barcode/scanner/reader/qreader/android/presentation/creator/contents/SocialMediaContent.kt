package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRTextField
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateMode
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.socialMedia.SocialMediaContentEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.socialMedia.SocialMediaContentViewModel

@Composable
fun SocialMediaContent(
    viewModel: SocialMediaContentViewModel = viewModel(),
    mode: GenerateMode,
    encoded: String,
    onContent: (Boolean, String, String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(mode) {
        viewModel.onEvent(SocialMediaContentEvent.SetGenerateMode(mode))
    }

    LaunchedEffect(state) {
        onContent(state.isEnabled, state.encode(), state.decode())
    }

    LaunchedEffect(encoded) {
        viewModel.onEvent(SocialMediaContentEvent.Encoded(encoded))
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        QRTextField(
            value = state.username,
            onValueChange = {
                viewModel.onEvent(SocialMediaContentEvent.UsernameChanged(it))
            },
            placeholder = mode.inputPlaceholder(),
            hint = mode.inputHint(),
            keyboardType = if (mode == GenerateMode.WhatsApp) {
                KeyboardType.Phone
            } else {
                KeyboardType.Text
            }
        )
    }
}

@Composable
private fun GenerateMode.inputHint(): String {
    val strings = LocalStrings.current

    return when (this) {
        GenerateMode.Instagram,
        GenerateMode.Facebook,
        GenerateMode.Twitter,
        GenerateMode.TikTok,
        GenerateMode.Telegram,
        GenerateMode.VKontakte,
        GenerateMode.Github,
        GenerateMode.Medium -> strings.socialMediaUsername(title)

        GenerateMode.Youtube,
        GenerateMode.Twitch -> strings.socialMediaChannel(title)

        GenerateMode.LinkedIn,
        GenerateMode.Dribbble,
        GenerateMode.Behance -> strings.socialMediaProfile(title)

        GenerateMode.WhatsApp -> strings.whatsappNumber

        else -> strings.text
    }
}

@Composable
private fun GenerateMode.inputPlaceholder(): String {
    val strings = LocalStrings.current

    return when (this) {
        GenerateMode.Instagram -> "e.g. cristiano"
        GenerateMode.Facebook -> "e.g. cristiano"
        GenerateMode.Twitter -> "e.g. elonmusk"
        GenerateMode.TikTok -> "e.g. khaby.lame"
        GenerateMode.Telegram -> "e.g. durov"
        GenerateMode.VKontakte -> "e.g. durov"
        GenerateMode.Github -> "e.g. freeCodeCamp"
        GenerateMode.Medium -> "e.g. swlh"
        GenerateMode.Youtube -> "e.g. MrBeast"
        GenerateMode.Twitch -> "e.g. ninja"
        GenerateMode.LinkedIn -> "e.g. williamhgates"
        GenerateMode.Dribbble -> "e.g. zhenyary"
        GenerateMode.Behance -> "e.g. zekadesign"
        GenerateMode.WhatsApp -> strings.egPlaceholderPhone
        else -> strings.enterValue
    }
}