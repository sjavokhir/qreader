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
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateType
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.socialMedia.SocialMediaContentEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.socialMedia.SocialMediaContentViewModel

@Composable
fun SocialMediaContent(
    viewModel: SocialMediaContentViewModel = viewModel(),
    type: GenerateType,
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
            keyboardType = if (type == GenerateType.WhatsApp) {
                KeyboardType.Phone
            } else {
                KeyboardType.Text
            }
        )
    }
}

private val GenerateType.inputHint: String
    @Composable
    get() = when (this) {
        GenerateType.Instagram,
        GenerateType.Facebook,
        GenerateType.Twitter,
        GenerateType.TikTok,
        GenerateType.Telegram,
        GenerateType.VKontakte,
        GenerateType.Github,
        GenerateType.Medium -> stringResource(id = R.string.social_media_username, title)

        GenerateType.Youtube,
        GenerateType.Twitch -> stringResource(id = R.string.social_media_channel, title)

        GenerateType.LinkedIn,
        GenerateType.Dribbble,
        GenerateType.Behance -> stringResource(id = R.string.social_media_profile, title)

        GenerateType.WhatsApp -> stringResource(id = R.string.whatsapp_number)

        else -> stringResource(id = R.string.text)
    }

private val GenerateType.inputPlaceholder: String
    @Composable
    get() = when (this) {
        GenerateType.Instagram -> "e.g. cristiano"
        GenerateType.Facebook -> "e.g. cristiano"
        GenerateType.Twitter -> "e.g. elonmusk"
        GenerateType.TikTok -> "e.g. khaby.lame"
        GenerateType.Telegram -> "e.g. durov"
        GenerateType.VKontakte -> "e.g. durov"
        GenerateType.Github -> "e.g. freeCodeCamp"
        GenerateType.Medium -> "e.g. swlh"
        GenerateType.Youtube -> "e.g. MrBeast"
        GenerateType.Twitch -> "e.g. ninja"
        GenerateType.LinkedIn -> "e.g. williamhgates"
        GenerateType.Dribbble -> "e.g. zhenyary"
        GenerateType.Behance -> "e.g. zekadesign"
        GenerateType.WhatsApp -> stringResource(id = R.string.eg_placeholder_phone)
        else -> stringResource(id = R.string.enter_value)
    }