package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.addContent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.drawableId
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.model.EditContentModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.components.QRFilledButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.design.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents.BizContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents.BusinessContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents.ContactContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents.EmailContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents.EventContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents.LocationContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents.PhoneContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents.SmsContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents.SocialMediaContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents.TextContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents.WebsiteContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.contents.WifiContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.DateTimePickerScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.LocationPickerScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.destinations.QRCodeScreenDestination
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.GenerateMode
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.spec.Direction

@Destination
@Composable
fun AddContentScreen(
    id: String,
    generateMode: GenerateMode,
    encoded: String = "",
    isEditable: Boolean = false,
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<EditContentModel>,
    resultTimestamp: ResultRecipient<DateTimePickerScreenDestination, Long>,
    resultLocation: ResultRecipient<LocationPickerScreenDestination, String>,
) {
    val context = LocalContext.current
    val strings = LocalStrings.current

    var isEnabled by remember { mutableStateOf(false) }
    var encodedContent by remember { mutableStateOf("") }
    var decodedContent by remember { mutableStateOf("") }

    QRBackground {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        context.drawableId(generateMode.icon)?.let { icon ->
                            Image(
                                painter = painterResource(id = icon),
                                contentDescription = generateMode.title,
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(MaterialTheme.shapes.medium)
                            )

                            Text(
                                text = generateMode.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
                item {
                    GenerateContent(
                        mode = generateMode,
                        encoded = encoded,
                        onContent = { enabled, encoded, decoded ->
                            isEnabled = enabled
                            encodedContent = encoded
                            decodedContent = decoded
                        },
                        onNavigate = navigator::navigate,
                        resultTimestamp = resultTimestamp,
                        resultLocation = resultLocation,
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(70.dp))
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .align(Alignment.BottomCenter),
            ) {
                DividerContent()

                QRFilledButton(
                    text = strings.next,
                    enabled = isEnabled,
                    onClick = {
                        if (isEditable) {
                            resultNavigator.navigateBack(
                                EditContentModel(encodedContent, decodedContent)
                            )
                        } else {
                            navigator.navigate(
                                QRCodeScreenDestination(
                                    id = id,
                                    isScanned = false,
                                    generateMode = generateMode,
                                    encoded = encodedContent,
                                    decoded = decodedContent
                                )
                            )
                        }
                    },
                    modifier = Modifier.padding(
                        horizontal = 20.dp,
                        vertical = 16.dp
                    )
                )
            }
        }
    }
}

@Composable
private fun GenerateContent(
    mode: GenerateMode,
    encoded: String,
    onContent: (Boolean, String, String) -> Unit,
    onNavigate: (Direction) -> Unit,
    resultTimestamp: ResultRecipient<DateTimePickerScreenDestination, Long>,
    resultLocation: ResultRecipient<LocationPickerScreenDestination, String>,
) {
    when (mode) {
        GenerateMode.Text -> TextContent(
            encoded = encoded,
            onContent = onContent
        )

        GenerateMode.Website -> WebsiteContent(
            encoded = encoded,
            onContent = onContent
        )

        GenerateMode.Sms -> SmsContent(
            encoded = encoded,
            onContent = onContent
        )

        GenerateMode.PhoneNumber -> PhoneContent(
            encoded = encoded,
            onContent = onContent
        )

        GenerateMode.EmailAddress -> EmailContent(
            encoded = encoded,
            onContent = onContent
        )

        GenerateMode.Wifi -> WifiContent(
            encoded = encoded,
            onContent = onContent
        )

        GenerateMode.ContactVCard -> ContactContent(
            encoded = encoded,
            onContent = onContent
        )

        GenerateMode.CalendarEvent -> EventContent(
            encoded = encoded,
            onContent = onContent,
            onNavigate = onNavigate,
            resultTimestamp = resultTimestamp
        )

        GenerateMode.BizCard -> BizContent(
            encoded = encoded,
            onContent = onContent
        )

        GenerateMode.BusinessVCard -> BusinessContent(
            encoded = encoded,
            onContent = onContent
        )

        GenerateMode.Location -> LocationContent(
            encoded = encoded,
            onContent = onContent,
            onNavigate = onNavigate,
            resultLocation = resultLocation
        )

        else -> {
            SocialMediaContent(
                mode = mode,
                encoded = encoded,
                onContent = onContent
            )
        }
    }
}

@Composable
private fun DividerContent() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp),
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
    )
}