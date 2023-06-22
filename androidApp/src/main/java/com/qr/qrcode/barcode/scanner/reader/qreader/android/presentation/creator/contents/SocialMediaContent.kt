package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRTextField
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateMode
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.socialMedia.SocialMediaContentEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.socialMedia.SocialMediaContentViewModel

@Composable
fun SocialMediaContent(
    viewModel: SocialMediaContentViewModel = viewModel(),
    type: GenerateMode,
    onContent: (Boolean, String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(type) {
        viewModel.onEvent(SocialMediaContentEvent.SetType(type))
    }

    LaunchedEffect(state) {
        onContent(state.isEnabled, state.generateText)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        QRTextField(
            value = state.username,
            onValueChange = {
                viewModel.onEvent(SocialMediaContentEvent.UsernameChanged(it))
            },
            placeholder = type.inputPlaceholder,
            hint = type.inputHint,
            keyboardType = if (type == GenerateMode.WhatsApp) {
                KeyboardType.Phone
            } else {
                KeyboardType.Text
            }
        )
    }
}

private val GenerateMode.inputHint: String
    @Composable
    get() = when (this) {
        GenerateMode.Instagram,
        GenerateMode.Facebook,
        GenerateMode.Twitter,
        GenerateMode.TikTok,
        GenerateMode.Telegram,
        GenerateMode.VKontakte,
        GenerateMode.Github,
        GenerateMode.Medium -> stringResource(id = R.string.social_media_username, title)

        GenerateMode.Youtube,
        GenerateMode.Twitch -> stringResource(id = R.string.social_media_channel, title)

        GenerateMode.LinkedIn,
        GenerateMode.Dribbble,
        GenerateMode.Behance -> stringResource(id = R.string.social_media_profile, title)

        GenerateMode.WhatsApp -> stringResource(id = R.string.whatsapp_number)

        else -> stringResource(id = R.string.text)
    }

private val GenerateMode.inputPlaceholder: String
    @Composable
    get() = when (this) {
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
        GenerateMode.WhatsApp -> stringResource(id = R.string.eg_placeholder_phone)
        else -> stringResource(id = R.string.enter_value)
    }