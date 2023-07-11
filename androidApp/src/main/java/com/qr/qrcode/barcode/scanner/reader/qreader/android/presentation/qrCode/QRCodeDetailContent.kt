package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.qrCode

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.addContact
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.clickableSingle
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.connectToWifi
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.copyWifiNetworkName
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.copyWifiPassword
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.openUrl
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.searchText
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.sendMail
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.sendSms
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.showLocation
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.helpers.ImageUtils
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.helpers.storagePermissions
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.model.QRCustomizeModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.CustomizeContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRFilledButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRImageContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QROutlinedButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.rememberQRDrawable
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.theme.LocalSubscription
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateMode
import com.ramcosta.composedestinations.spec.Direction

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QRDetailContent(
    dateTime: String,
    generateMode: GenerateMode,
    encoded: String,
    decoded: String,
    customize: QRCustomizeModel,
    isEditable: Boolean = false,
    onNavigate: (Direction) -> Unit
) {
    val context = LocalContext.current
    val hasSubscription = LocalSubscription.current

    val storagePermissionsState = rememberMultiplePermissionsState(storagePermissions)

    val qrDrawable = rememberQRDrawable(
        content = encoded,
        customize = customize,
        ownLogo = ImageUtils.getDrawableFromPath(context, customize.ownLogoPath)
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(20.dp)
    ) {
        item {
            CustomizeContent(
                customize = customize,
                hasSubscription = hasSubscription,
                onNavigate = onNavigate
            )
        }
        item {
            QRImageContent(qrDrawable)
        }
        item {
            when (generateMode) {
                GenerateMode.Text -> TextContent(encoded, decoded, dateTime)
                GenerateMode.Website -> WebsiteContent(encoded, decoded, dateTime)
                GenerateMode.Sms -> SmsContent(encoded, decoded, dateTime)
                GenerateMode.PhoneNumber -> PhoneContent(encoded, decoded, dateTime)
                GenerateMode.EmailAddress -> EmailContent(encoded, decoded, dateTime)
                GenerateMode.Wifi -> WifiContent(encoded, decoded, dateTime)
                GenerateMode.ContactVCard -> TODO()
                GenerateMode.CalendarEvent -> TODO()
                GenerateMode.BizCard -> TODO()
                GenerateMode.BusinessVCard -> TODO()
                GenerateMode.Location -> LocationContent(encoded, decoded, dateTime)
                else -> WebsiteContent(encoded, decoded, dateTime)
            }
        }
        item {
            ActionsContent(
                isEditable = isEditable,
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
private fun LocationContent(
    encoded: String,
    decoded: String,
    dateTime: String
) {
    val strings = LocalStrings.current
    val context = LocalContext.current

    DecodeContent(decoded, dateTime) {
        ContentActionButton(strings.showLocation, R.drawable.ic_show_location) {
            context.showLocation(encoded)
        }
    }
}

@Composable
private fun WifiContent(
    encoded: String,
    decoded: String,
    dateTime: String
) {
    val strings = LocalStrings.current
    val context = LocalContext.current

    DecodeContent(decoded, dateTime) {
        ContentActionButton(strings.connectToWifi, R.drawable.ic_wifi) {
            context.connectToWifi(encoded)
        }

        ContentActionButton(strings.copyNetworkName, R.drawable.ic_copy) {
            context.copyWifiNetworkName(encoded)
        }

        ContentActionButton(strings.copyPassword, R.drawable.ic_copy) {
            context.copyWifiPassword(encoded)
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
    isEditable: Boolean,
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