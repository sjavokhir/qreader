package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.history

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.addContact
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.clickableSingle
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.openUrl
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.searchText
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.sendMail
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.sendSms
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.helpers.ImageUtils
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.helpers.storagePermissions
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.CustomizeContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRFilledButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRImageContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QROutlinedButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.theme.LocalSubscription
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.qrCode.QRCustomizeModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.qrCode.rememberQRDrawable
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.qrCode.toState
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.AddContentScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.CustomizeScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateMode
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.qrCode.QRCodeEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.qrCode.QRCodeViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.spec.Direction

@Destination
@Composable
fun HistoryDetailScreen(
    id: String,
    dateTime: String,
    generateMode: GenerateMode,
    encoded: String,
    decoded: String,
    model: QRCustomizeModel,
    viewModel: QRCodeViewModel = viewModel(),
    navigator: DestinationsNavigator,
    resultCustomization: ResultRecipient<CustomizeScreenDestination, QRCustomizeModel>,
    resultAddContent: ResultRecipient<AddContentScreenDestination, QRCustomizeModel>,
) {
    var customizeModel by rememberSaveable { mutableStateOf(model) }
    var encodedValue by rememberSaveable { mutableStateOf(encoded) }
    var decodedValue by rememberSaveable { mutableStateOf(decoded) }

    resultCustomization.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {}
            is NavResult.Value -> {
                customizeModel = result.value

                viewModel.onEvent(
                    QRCodeEvent.InsertHistory(
                        id = id,
                        mode = generateMode,
                        encoded = encodedValue,
                        decoded = decodedValue,
                        customize = result.value.toState()
                    )
                )
            }
        }
    }

    resultAddContent.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {}
            is NavResult.Value -> {
                customizeModel = result.value

                viewModel.onEvent(
                    QRCodeEvent.InsertHistory(
                        id = id,
                        mode = generateMode,
                        encoded = encodedValue,
                        decoded = decodedValue,
                        customize = result.value.toState()
                    )
                )
            }
        }
    }

    QRBackground {
        HistoryDetailScreenContent(
            hasSubscription = LocalSubscription.current,
            dateTime = dateTime,
            generateMode = generateMode,
            encodedValue = encodedValue,
            decodedValue = decodedValue,
            model = customizeModel,
            onNavigate = navigator::navigate
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun HistoryDetailScreenContent(
    hasSubscription: Boolean,
    dateTime: String,
    generateMode: GenerateMode,
    encodedValue: String,
    decodedValue: String,
    model: QRCustomizeModel,
    onNavigate: (Direction) -> Unit
) {
    val context = LocalContext.current
    val qrDrawable = rememberQRDrawable(
        content = encodedValue,
        model = model,
        ownLogo = ImageUtils.getDrawableFromPath(context, model.ownLogoPath)
    )

    val storagePermissionsState = rememberMultiplePermissionsState(storagePermissions)

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(20.dp)
    ) {
        item {
            CustomizeContent(
                model = model,
                hasSubscription = hasSubscription,
                onNavigate = onNavigate
            )
        }
        item {
            QRImageContent(qrDrawable)
        }
        item {
            when (generateMode) {
                GenerateMode.Text -> TextContent(encodedValue, decodedValue, dateTime)
                GenerateMode.Website -> WebsiteContent(encodedValue, decodedValue, dateTime)
                GenerateMode.Sms -> SmsContent(encodedValue, decodedValue, dateTime)
                GenerateMode.PhoneNumber -> PhoneContent(encodedValue, decodedValue, dateTime)
                GenerateMode.EmailAddress -> EmailContent(encodedValue, decodedValue, dateTime)
                GenerateMode.Wifi -> TODO()
                GenerateMode.ContactVCard -> TODO()
                GenerateMode.CalendarEvent -> TODO()
                GenerateMode.BizCard -> TODO()
                GenerateMode.BusinessVCard -> TODO()
                GenerateMode.Location -> TODO()
                else -> WebsiteContent(encodedValue, decodedValue, dateTime)
            }
        }
        item {
            ActionsContent(
                onSave = {
                    if (storagePermissionsState.allPermissionsGranted) {
                        ImageUtils.saveDrawableToGallery(
                            context = context,
                            drawable = qrDrawable
                        )
                    } else {
                        storagePermissionsState.launchMultiplePermissionRequest()
                    }
                },
                onShare = {
                    if (storagePermissionsState.allPermissionsGranted) {
                        ImageUtils.shareDrawable(
                            context = context,
                            drawable = qrDrawable
                        )
                    } else {
                        storagePermissionsState.launchMultiplePermissionRequest()
                    }
                }
            )
        }
    }
}

@Composable
private fun TextContent(
    encoded: String,
    decoded: String,
    dateTime: String
) {
    val strings = LocalStrings.current
    val context = LocalContext.current

    DecodeContent(decoded, dateTime) {
        ContentActionButton(strings.searchOnWeb, R.drawable.ic_search) {
            context.searchText(encoded)
        }
    }
}

@Composable
private fun WebsiteContent(
    encoded: String,
    decoded: String,
    dateTime: String
) {
    val strings = LocalStrings.current
    val context = LocalContext.current

    DecodeContent(decoded, dateTime) {
        ContentActionButton(strings.openWebsite, R.drawable.ic_open_website) {
            context.openUrl(encoded)
        }
    }
}

@Composable
private fun SmsContent(
    encoded: String,
    decoded: String,
    dateTime: String
) {
    val strings = LocalStrings.current
    val context = LocalContext.current

    DecodeContent(decoded, dateTime) {
        ContentActionButton(strings.sendSms, R.drawable.ic_sms) {
            context.sendSms(encoded)
        }

        ContentActionButton(strings.addContact, R.drawable.ic_add_contact) {
            context.addContact(encoded)
        }
    }
}

@Composable
private fun PhoneContent(
    encoded: String,
    decoded: String,
    dateTime: String
) {
    val strings = LocalStrings.current
    val context = LocalContext.current

    DecodeContent(decoded, dateTime) {
        ContentActionButton(strings.addContact, R.drawable.ic_add_contact) {
            context.addContact(encoded)
        }
    }
}

@Composable
private fun EmailContent(
    encoded: String,
    decoded: String,
    dateTime: String
) {
    val strings = LocalStrings.current
    val context = LocalContext.current

    DecodeContent(decoded, dateTime) {
        ContentActionButton(strings.sendEmail, R.drawable.ic_mail) {
            context.sendMail(encoded)
        }
    }
}

@Composable
private fun DecodeContent(
    decoded: String,
    dateTime: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = decoded,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = dateTime,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.outline
            )
        }

        content()
    }
}

@Composable
private fun ContentActionButton(
    text: String,
    @DrawableRes icon: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.clickableSingle(
            onClick = onClick,
            hasIndication = false
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = text,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .padding(7.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun ActionsContent(
    onSave: () -> Unit,
    onShare: () -> Unit
) {
    val strings = LocalStrings.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        QROutlinedButton(
            text = strings.save,
            onClick = onSave,
            modifier = Modifier.weight(1f),
            leadingIcon = painterResource(id = R.drawable.ic_save)
        )

        QRFilledButton(
            text = strings.share,
            onClick = onShare,
            modifier = Modifier.weight(1f),
            leadingIcon = painterResource(id = R.drawable.ic_share)
        )
    }
}