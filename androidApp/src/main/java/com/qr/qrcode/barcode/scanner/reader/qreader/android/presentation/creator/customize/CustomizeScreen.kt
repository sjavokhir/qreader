package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.customize

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.clickableSingle
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.dashedBorder
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.drawableId
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.helpers.ImageUtils
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.DividerContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRFilledButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRIcon
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QROutlinedButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRTextField
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.localization.LocalStrings
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.qrCode.QRCustomizeModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.qrCode.toModel
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.qrCode.toState
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.picker.colorpicker.extensions.toColor
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.picker.colorpicker.extensions.toHex
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.picker.colorpicker.pickers.ColorPickerDialog
import com.qr.qrcode.barcode.scanner.reader.qreader.core.extensions.tryCatch
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.customize.ColorPickerType
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.customize.CustomizeEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.customize.CustomizeState
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.customize.CustomizeViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator

@Destination
@Composable
fun CustomizeScreen(
    model: QRCustomizeModel,
    viewModel: CustomizeViewModel = viewModel(),
    resultNavigator: ResultBackNavigator<QRCustomizeModel>
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(model) {
        viewModel.onEvent(CustomizeEvent.Customize(model.toState()))
    }

    QRBackground {
        CustomizeScreenContent(
            state = state,
            onEvent = viewModel::onEvent,
            resultNavigator = resultNavigator
        )
    }
}

@Composable
private fun CustomizeScreenContent(
    state: CustomizeState,
    onEvent: (CustomizeEvent) -> Unit,
    resultNavigator: ResultBackNavigator<QRCustomizeModel>
) {
    val context = LocalContext.current
    val strings = LocalStrings.current

    var logoUri by remember { mutableStateOf<Uri?>(null) }
    val logoBitmap = rememberOwnLogo(context, logoUri)

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { logoUri = it }

    LaunchedEffect(state.ownLogoPath) {
        if (!state.ownLogoPath.isNullOrEmpty()) {
            tryCatch {
                logoUri = Uri.parse(state.ownLogoPath)
            }
        }
    }

    if (state.showColorPicker) {
        ColorPickerDialog(
            onDismissRequest = {
                onEvent(CustomizeEvent.DismissColorPicker)
            },
            onPickedColor = {
                onEvent(CustomizeEvent.SelectColor(it.toHex()))
            }
        )
    }

    if (state.showPreview) {
        QRPreviewDialog(
            ownLogo = ImageUtils.getDrawableFromUri(context, logoUri),
            model = state.toModel(logoUri),
            onDismissRequest = {
                onEvent(CustomizeEvent.ShowHidePreview(false))
            }
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(28.dp),
            contentPadding = PaddingValues(vertical = 20.dp)
        ) {
            item {
                QRCodePatternContent(
                    context = context,
                    state = state,
                    onEvent = onEvent
                )
            }
            item {
                DividerContent(
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
            item {
                CornerStyleContent(
                    context = context,
                    state = state,
                    onEvent = onEvent
                )
            }
            item {
                DividerContent(
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
            item {
                AddLogoContent(
                    context = context,
                    state = state,
                    onEvent = onEvent,
                    logo = logoBitmap,
                    onUpload = {
                        imageLauncher.launch("image/*")
                    },
                    onDelete = {
                        logoUri = null
                    }
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

            Row(
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 16.dp
                ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                QROutlinedButton(
                    text = strings.preview,
                    onClick = {
                        onEvent(CustomizeEvent.ShowHidePreview(true))
                    },
                    modifier = Modifier.weight(1f)
                )

                QRFilledButton(
                    text = strings.customize,
                    onClick = {
                        resultNavigator.navigateBack(state.toModel(logoUri))
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun QRCodePatternContent(
    context: Context,
    state: CustomizeState,
    onEvent: (CustomizeEvent) -> Unit
) {
    val strings = LocalStrings.current

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = strings.qrCodePattern,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            items(state.patterns) { pattern ->
                SelectPatternContent(
                    context = context,
                    icon = pattern.icon,
                    isSelected = pattern == state.selectedPattern,
                    onClick = {
                        onEvent(CustomizeEvent.SelectPattern(pattern))
                    }
                )
            }
        }

        Row(
            modifier = Modifier.padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            QRTextField(
                baseModifier = Modifier.weight(1f),
                modifier = Modifier.clickableSingle(
                    onClick = {
                        onEvent(CustomizeEvent.ShowColorPicker(ColorPickerType.PatternDotColor))
                    },
                    hasIndication = false
                ),
                value = "#${state.patternDotHex}",
                onValueChange = {},
                hint = strings.dotColor,
                trailingIcon = {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = CircleShape
                            )
                            .background(state.patternDotHex.toColor())
                    )
                },
                readOnly = true
            )

            QRTextField(
                baseModifier = Modifier.weight(1f),
                modifier = Modifier.clickableSingle(
                    onClick = {
                        onEvent(CustomizeEvent.ShowColorPicker(ColorPickerType.PatternBackgroundColor))
                    },
                    hasIndication = false
                ),
                value = "#${state.patternBackgroundHex}",
                onValueChange = {},
                hint = strings.backgroundColor,
                trailingIcon = {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = CircleShape
                            )
                            .background(state.patternBackgroundHex.toColor())
                    )
                },
                readOnly = true
            )
        }
    }
}

@Composable
private fun SelectPatternContent(
    context: Context,
    icon: String,
    size: Dp = 80.dp,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(MaterialTheme.shapes.medium)
            .border(
                width = if (isSelected) (1.5).dp else 1.dp,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                },
                shape = MaterialTheme.shapes.medium
            )
            .background(MaterialTheme.colorScheme.background)
            .clickableSingle(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        context.drawableId(icon)?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = icon,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.outline)
            )
        }
    }
}

@Composable
private fun CornerStyleContent(
    context: Context,
    state: CustomizeState,
    onEvent: (CustomizeEvent) -> Unit
) {
    val strings = LocalStrings.current

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = strings.cornerStyle,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = strings.frameStyle,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                items(state.corners) { corner ->
                    SelectPatternContent(
                        context = context,
                        icon = corner.icon,
                        size = 56.dp,
                        isSelected = corner == state.selectedCorner,
                        onClick = {
                            onEvent(CustomizeEvent.SelectCorner(corner))
                        }
                    )
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = strings.dotsStyle,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                items(state.dots) { dot ->
                    SelectPatternContent(
                        context = context,
                        icon = dot.icon,
                        size = 56.dp,
                        isSelected = dot == state.selectedDot,
                        onClick = {
                            onEvent(CustomizeEvent.SelectDot(dot))
                        }
                    )
                }
            }
        }

        Row(
            modifier = Modifier.padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            QRTextField(
                baseModifier = Modifier.weight(1f),
                modifier = Modifier.clickableSingle(
                    onClick = {
                        onEvent(CustomizeEvent.ShowColorPicker(ColorPickerType.FrameColor))
                    },
                    hasIndication = false
                ),
                value = "#${state.frameHex}",
                onValueChange = {},
                hint = strings.frameColor,
                trailingIcon = {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = CircleShape
                            )
                            .background(state.frameHex.toColor())
                    )
                },
                readOnly = true
            )

            QRTextField(
                baseModifier = Modifier.weight(1f),
                modifier = Modifier.clickableSingle(
                    onClick = {
                        onEvent(CustomizeEvent.ShowColorPicker(ColorPickerType.FrameDotColor))
                    },
                    hasIndication = false
                ),
                value = "#${state.frameDotHex}",
                onValueChange = {},
                hint = strings.dotsColor,
                trailingIcon = {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = CircleShape
                            )
                            .background(state.frameDotHex.toColor())
                    )
                },
                readOnly = true
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AddLogoContent(
    context: Context,
    state: CustomizeState,
    onEvent: (CustomizeEvent) -> Unit,
    logo: Bitmap?,
    onUpload: () -> Unit,
    onDelete: () -> Unit
) {
    val strings = LocalStrings.current

    Column {
        Text(
            text = strings.addLogo,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        FlowRow(
            modifier = Modifier.padding(14.dp)
        ) {
            state.logos.forEach {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            color = if (it == state.selectedLogo) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                Color.Transparent
                            },
                            shape = CircleShape
                        )
                        .clickableSingle { onEvent(CustomizeEvent.SelectLogo(it)) },
                    contentAlignment = Alignment.Center
                ) {
                    context.drawableId(it)?.let { drawableId ->
                        Image(
                            painter = painterResource(id = drawableId),
                            contentDescription = it,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                    }
                }
            }
        }

        Text(
            text = strings.uploadYourOwnLogo,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 10.dp, bottom = 16.dp)
        )

        if (logo != null) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    bitmap = logo.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(10.dp)
                )

                Text(
                    text = strings.delete,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickableSingle(
                            onClick = onDelete,
                            hasIndication = false
                        )
                        .padding(vertical = 10.dp)
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .dashedBorder(
                        width = (1.5).dp,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                        shape = MaterialTheme.shapes.medium,
                        on = 8.dp,
                        off = 4.dp
                    )
                    .clickableSingle(onClick = onUpload)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                QRIcon(
                    painter = painterResource(id = R.drawable.ic_add_photo),
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(48.dp)
                )

                Text(
                    text = strings.uploadImage,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = strings.maximumSize,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Composable
private fun rememberOwnLogo(
    context: Context,
    uri: Uri?
): Bitmap? {
    uri ?: return null

    return try {
        if (Build.VERSION.SDK_INT < 28) {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }
    } catch (_: Throwable) {
        null
    }
}