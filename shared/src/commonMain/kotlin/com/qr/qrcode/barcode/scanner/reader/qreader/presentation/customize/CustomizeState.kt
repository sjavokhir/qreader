package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.customize

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRCornerType
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRDotType
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRPatternType

data class CustomizeState(
    val patterns: List<QRPatternType> = emptyList(),
    val selectedPattern: QRPatternType = QRPatternType.Square,
    val corners: List<QRCornerType> = emptyList(),
    val selectedCorner: QRCornerType = QRCornerType.NoStyle,
    val dots: List<QRDotType> = emptyList(),
    val selectedDot: QRDotType = QRDotType.NoStyle,
    val showColorPicker: Boolean = false,
    val colorPickerType: ColorPickerType = ColorPickerType.PatternDotColor,
    val patternDotHex: String = "FF000000",
    val patternBackgroundHex: String = "FFFFFFFF",
    val cornerHex: String = "FF000000",
    val cornerDotHex: String = "FF000000",
    val logos: List<String> = emptyList(),
    val selectedLogo: String = "",
    val showPreview: Boolean = false,
)
