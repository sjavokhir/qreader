package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.customize

import android.content.Context
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.DividerContent
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRBackground
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRFilledButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRIcon
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QROutlinedButton
import com.qr.qrcode.barcode.scanner.reader.qreader.android.designsystem.components.QRTextField
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.picker.colorpicker.extensions.toColor
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.picker.colorpicker.extensions.toHex
import com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.picker.colorpicker.pickers.ColorPickerDialog
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.customize.ColorPickerType
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.customize.CustomizeEvent
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.customize.CustomizeState
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.customize.CustomizeViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator

@Destination
@Composable
fun CustomizeScreen(
    viewModel: CustomizeViewModel = viewModel(),
    resultNavigator: ResultBackNavigator<String>
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

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
    resultNavigator: ResultBackNavigator<String>
) {
    val context = LocalContext.current

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
            state = state,
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
                    onEvent = onEvent
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
                    text = stringResource(id = R.string.action_preview),
                    onClick = {
                        onEvent(CustomizeEvent.ShowHidePreview(true))
                    },
                    modifier = Modifier.weight(1f)
                )

                QRFilledButton(
                    text = stringResource(id = R.string.customize),
                    onClick = {
                        resultNavigator.navigateBack("Result")
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
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.qr_code_pattern),
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
                hint = stringResource(id = R.string.dot_color),
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
                hint = stringResource(id = R.string.background_color),
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
        if (icon.isEmpty()) {
            Image(
                painter = painterResource(id = R.drawable.ic_none),
                contentDescription = icon,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.outline)
            )
        } else {
            context.drawableId(icon)?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = icon,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.outline)
                )
            }
        }
    }
}

@Composable
private fun CornerStyleContent(
    context: Context,
    state: CustomizeState,
    onEvent: (CustomizeEvent) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.corner_style),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.frame_around_corner_dots_style),
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
                text = stringResource(id = R.string.corner_dots_type),
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
                        onEvent(CustomizeEvent.ShowColorPicker(ColorPickerType.CornerColor))
                    },
                    hasIndication = false
                ),
                value = "#${state.cornerHex}",
                onValueChange = {},
                hint = stringResource(id = R.string.frame_around_corner_dots_color),
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
                            .background(state.cornerHex.toColor())
                    )
                },
                readOnly = true
            )

            QRTextField(
                baseModifier = Modifier.weight(1f),
                modifier = Modifier.clickableSingle(
                    onClick = {
                        onEvent(CustomizeEvent.ShowColorPicker(ColorPickerType.CornerDotColor))
                    },
                    hasIndication = false
                ),
                value = "#${state.cornerDotHex}",
                onValueChange = {},
                hint = stringResource(id = R.string.corner_dots_color),
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
                            .background(state.cornerDotHex.toColor())
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
    onEvent: (CustomizeEvent) -> Unit
) {
    Column {
        Text(
            text = stringResource(id = R.string.add_logo),
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
            text = stringResource(id = R.string.upload_your_own_logo),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 10.dp, bottom = 16.dp)
        )

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
                .clickableSingle {}
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
                text = stringResource(id = R.string.upload_image),
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = stringResource(id = R.string.maximum_size),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}