package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.qrCode

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.addContact
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.addToCalendar
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.clickableSingle
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.connectToWifi
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.copyToClipboard
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.dial
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.openUrl
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.searchGoogle
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.sendMail
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.sendSms
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.showAddress
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.showLocation
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.helpers.ImageUtils
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.helpers.storagePermissions
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.model.QRCustomizeModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.CustomizeContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRIcon
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRImageContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.rememberQRDrawable
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.theme.LocalSubscription
import com.qr.qrcode.barcode.scanner.reader.qreader.core.datetime.currentTimestamp
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateMode
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.biz.toBizContent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.business.toBusinessContent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.contact.toContactContent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.email.toEmailContent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.event.toEventContent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.location.toLocationContent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.phone.toPhoneContent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.sms.toSmsContent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.creator.wifi.toWifiContent
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
    isDeletable: Boolean = false,
    isChromeCustomTabs: Boolean = false,
    onNavigate: (Direction) -> Unit,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {}
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
                GenerateMode.Text -> {
                    TextContent(encoded, decoded, dateTime, isChromeCustomTabs)
                }

                GenerateMode.Website -> {
                    WebsiteContent(encoded, decoded, dateTime, isChromeCustomTabs)
                }

                GenerateMode.Sms -> SmsContent(encoded, decoded, dateTime)
                GenerateMode.PhoneNumber -> PhoneContent(encoded, decoded, dateTime)
                GenerateMode.EmailAddress -> EmailContent(encoded, decoded, dateTime)
                GenerateMode.Wifi -> WifiContent(encoded, decoded, dateTime)
                GenerateMode.ContactVCard -> ContactContent(encoded, decoded, dateTime)
                GenerateMode.CalendarEvent -> EventContent(encoded, decoded, dateTime)
                GenerateMode.BizCard -> {
                    BizCardContent(encoded, decoded, dateTime, isChromeCustomTabs)
                }

                GenerateMode.BusinessVCard -> {
                    BusinessContent(encoded, decoded, dateTime, isChromeCustomTabs)
                }

                GenerateMode.Location -> LocationContent(encoded, decoded, dateTime)
                else -> {
                    WebsiteContent(encoded, decoded, dateTime, isChromeCustomTabs)
                }
            }
        }
        item {
            ActionsContent(
                isEditable = isEditable,
                isDeletable = isDeletable,
                onSave = {
                    if (storagePermissionsState.allPermissionsGranted) {
                        ImageUtils.saveDrawableToGallery(context, qrDrawable)
                    } else {
                        storagePermissionsState.launchMultiplePermissionRequest()
                    }
                },
                onShare = {
                    if (storagePermissionsState.allPermissionsGranted) {
                        ImageUtils.shareDrawable(context, qrDrawable)
                    } else {
                        storagePermissionsState.launchMultiplePermissionRequest()
                    }
                },
                onCopy = {
                    context.copyToClipboard(encoded)
                },
                onEdit = onEdit,
                onDelete = onDelete
            )
        }
    }
}

@Composable
private fun TextContent(
    encoded: String,
    decoded: String,
    dateTime: String,
    isChromeCustomTabs: Boolean
) {
    val strings = LocalStrings.current
    val context = LocalContext.current

    DecodeContent(decoded, dateTime) {
        ContentActionButton(strings.searchOnWeb, R.drawable.ic_search) {
            context.searchGoogle(encoded, isChromeCustomTabs)
        }
    }
}

@Composable
private fun WebsiteContent(
    encoded: String,
    decoded: String,
    dateTime: String,
    isChromeCustomTabs: Boolean
) {
    val strings = LocalStrings.current
    val context = LocalContext.current

    DecodeContent(decoded, dateTime) {
        ContentActionButton(strings.openWebsite, R.drawable.ic_open_website) {
            context.openUrl(encoded, isChromeCustomTabs)
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

    val content = remember(encoded) { encoded.toSmsContent() }
    val phone = remember(content) { content?.phone.orEmpty() }
    val message = remember(content) { content?.message.orEmpty() }

    DecodeContent(decoded, dateTime) {
        if (phone.isNotEmpty()) {
            if (message.isNotEmpty()) {
                ContentActionButton(strings.sendSms, R.drawable.ic_sms) {
                    context.sendSms(phone, message)
                }
            }

            ContentActionButton(strings.addContact, R.drawable.ic_add_contact) {
                context.addContact(phone)
            }

            ContentActionButton(strings.dial(phone), R.drawable.ic_call) {
                context.dial(phone)
            }
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

    val phone = remember(encoded) { encoded.toPhoneContent()?.phone.orEmpty() }

    DecodeContent(decoded, dateTime) {
        if (phone.isNotEmpty()) {
            ContentActionButton(strings.addContact, R.drawable.ic_add_contact) {
                context.addContact(phone)
            }

            ContentActionButton(strings.dial(phone), R.drawable.ic_call) {
                context.dial(phone)
            }
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

    val content = remember(encoded) { encoded.toEmailContent() }
    val email = remember(content) { content?.email.orEmpty() }
    val subject = remember(content) { content?.subject.orEmpty() }
    val message = remember(content) { content?.message.orEmpty() }

    DecodeContent(decoded, dateTime) {
        if (email.isNotEmpty()) {
            ContentActionButton(strings.sendEmail, R.drawable.ic_mail) {
                context.sendMail(email, subject, message)
            }
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

    val content = remember(encoded) { encoded.toLocationContent() }
    val latitude = remember(content) { content?.latitude.orEmpty() }
    val longitude = remember(content) { content?.longitude.orEmpty() }

    DecodeContent(decoded, dateTime) {
        if (latitude.isNotEmpty() && longitude.isNotEmpty()) {
            ContentActionButton(strings.showLocation, R.drawable.ic_show_location) {
                context.showLocation(latitude, longitude)
            }
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

    val content = remember(encoded) { encoded.toWifiContent() }
    val networkName = remember(content) { content?.networkName.orEmpty() }
    val password = remember(content) { content?.password.orEmpty() }
    val authentication = remember(content) { content?.authentication }
    val isHidden = remember(content) { content?.isHidden ?: false }

    DecodeContent(decoded, dateTime) {
        ContentActionButton(strings.connectToWifi, R.drawable.ic_wifi) {
            context.connectToWifi(
                networkName,
                password,
                authentication,
                isHidden
            )
        }

        if (networkName.isNotEmpty()) {
            ContentActionButton(strings.copyNetworkName, R.drawable.ic_copy) {
                context.copyToClipboard(networkName)
            }
        }

        if (password.isNotEmpty()) {
            ContentActionButton(strings.copyPassword, R.drawable.ic_copy) {
                context.copyToClipboard(password)
            }
        }
    }
}

@Composable
private fun ContactContent(
    encoded: String,
    decoded: String,
    dateTime: String
) {
    val strings = LocalStrings.current
    val context = LocalContext.current

    val content = remember(encoded) { encoded.toContactContent() }
    val name = remember(content) { content?.name.orEmpty() }
    val phone = remember(content) { content?.phone.orEmpty() }
    val email = remember(content) { content?.email.orEmpty() }
    val address = remember(content) { content?.address.orEmpty() }

    DecodeContent(decoded, dateTime) {
        if (phone.isNotEmpty()) {
            ContentActionButton(strings.addContact, R.drawable.ic_add_contact) {
                context.addContact(
                    phone = phone,
                    name = name,
                    email = email,
                    address = address
                )
            }

            ContentActionButton(strings.dial(phone), R.drawable.ic_call) {
                context.dial(phone)
            }
        }

        if (email.isNotEmpty()) {
            ContentActionButton(strings.sendEmail, R.drawable.ic_mail) {
                context.sendMail(email)
            }
        }

        if (address.isNotEmpty()) {
            ContentActionButton(strings.viewAddress, R.drawable.ic_show_location) {
                context.showAddress(address)
            }
        }
    }
}

@Composable
private fun BusinessContent(
    encoded: String,
    decoded: String,
    dateTime: String,
    isChromeCustomTabs: Boolean,
) {
    val strings = LocalStrings.current
    val context = LocalContext.current

    val content = remember(encoded) { encoded.toBusinessContent() }
    val name = remember(content) { content?.name.orEmpty() }
    val industry = remember(content) { content?.industry.orEmpty() }
    val phone = remember(content) { content?.phone.orEmpty() }
    val email = remember(content) { content?.email.orEmpty() }
    val website = remember(content) { content?.website.orEmpty() }
    val address = remember(content) { content?.address.orEmpty() }

    DecodeContent(decoded, dateTime) {
        if (phone.isNotEmpty()) {
            ContentActionButton(strings.addContact, R.drawable.ic_add_contact) {
                context.addContact(
                    phone = phone,
                    name = name,
                    company = industry,
                    email = email,
                    address = address
                )
                context.addContact(phone)
            }

            ContentActionButton(strings.dial(phone), R.drawable.ic_call) {
                context.dial(phone)
            }
        }

        if (email.isNotEmpty()) {
            ContentActionButton(strings.sendEmail, R.drawable.ic_mail) {
                context.sendMail(email)
            }
        }

        if (website.isNotEmpty()) {
            ContentActionButton(strings.openWebsite, R.drawable.ic_open_website) {
                context.openUrl(website, isChromeCustomTabs)
            }
        }

        if (address.isNotEmpty()) {
            ContentActionButton(strings.viewAddress, R.drawable.ic_show_location) {
                context.showAddress(address)
            }
        }
    }
}

@Composable
private fun BizCardContent(
    encoded: String,
    decoded: String,
    dateTime: String,
    isChromeCustomTabs: Boolean,
) {
    val strings = LocalStrings.current
    val context = LocalContext.current

    val content = remember(encoded) { encoded.toBizContent() }
    val firstName = remember(content) { content?.firstName.orEmpty() }
    val lastName = remember(content) { content?.lastName.orEmpty() }
    val company = remember(content) { content?.company.orEmpty() }
    val job = remember(content) { content?.job.orEmpty() }
    val phone = remember(content) { content?.phone.orEmpty() }
    val email = remember(content) { content?.email.orEmpty() }
    val website = remember(content) { content?.website.orEmpty() }
    val address = remember(content) { content?.address.orEmpty() }

    DecodeContent(decoded, dateTime) {
        if (phone.isNotEmpty()) {
            ContentActionButton(strings.addContact, R.drawable.ic_add_contact) {
                context.addContact(
                    phone = phone,
                    name = "$firstName $lastName".trim(),
                    company = company,
                    job = job,
                    email = email,
                    address = address
                )
            }

            ContentActionButton(strings.dial(phone), R.drawable.ic_call) {
                context.dial(phone)
            }
        }

        if (email.isNotEmpty()) {
            ContentActionButton(strings.sendEmail, R.drawable.ic_mail) {
                context.sendMail(email)
            }
        }

        if (website.isNotEmpty()) {
            ContentActionButton(strings.openWebsite, R.drawable.ic_open_website) {
                context.openUrl(website, isChromeCustomTabs)
            }
        }

        if (address.isNotEmpty()) {
            ContentActionButton(strings.viewAddress, R.drawable.ic_show_location) {
                context.showAddress(address)
            }
        }
    }
}

@Composable
private fun EventContent(
    encoded: String,
    decoded: String,
    dateTime: String
) {
    val strings = LocalStrings.current
    val context = LocalContext.current

    val content = remember(encoded) { encoded.toEventContent() }

    DecodeContent(decoded, dateTime) {
        ContentActionButton(strings.addToCalendar, R.drawable.ic_today) {
            context.addToCalendar(
                name = content?.name.orEmpty(),
                location = content?.location.orEmpty(),
                description = content?.description.orEmpty(),
                isAllDay = content?.isAllDay ?: false,
                startMillis = content?.startTimestamp,
                endMillis = content?.endTimestamp
            )
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ActionsContent(
    isEditable: Boolean,
    isDeletable: Boolean,
    onSave: () -> Unit,
    onShare: () -> Unit,
    onCopy: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val strings = LocalStrings.current

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 4.dp,
            alignment = Alignment.CenterHorizontally
        ),
        verticalArrangement = Arrangement.spacedBy(
            space = 12.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        ActionButtonContent(
            text = strings.save,
            icon = R.drawable.ic_save,
            onClick = onSave
        )

        ActionButtonContent(
            text = strings.share,
            icon = R.drawable.ic_share,
            onClick = onShare
        )

        ActionButtonContent(
            text = strings.copy,
            icon = R.drawable.ic_copy,
            onClick = onCopy
        )

        if (isEditable) {
            ActionButtonContent(
                text = strings.edit,
                icon = R.drawable.ic_edit,
                onClick = onEdit
            )
        }

        if (isDeletable) {
            ActionButtonContent(
                text = strings.delete,
                icon = R.drawable.ic_delete,
                color = MaterialTheme.colorScheme.error,
                onClick = onDelete
            )
        }
    }
}

@Composable
private fun ActionButtonContent(
    text: String,
    @DrawableRes icon: Int,
    color: Color = MaterialTheme.colorScheme.onBackground,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .clickableSingle(onClick = onClick)
            .padding(
                horizontal = 12.dp,
                vertical = 4.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        QRIcon(
            painter = painterResource(id = icon),
            color = color
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = color
        )
    }
}