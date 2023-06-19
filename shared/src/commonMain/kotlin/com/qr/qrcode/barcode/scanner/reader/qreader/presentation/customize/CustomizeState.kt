package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.customize

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRCornerType
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRDotType
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRPatternType

data class CustomizeState(
    val patterns: List<QRPatternType> = emptyList(),
    val selectedPattern: QRPatternType = QRPatternType.Square,
    val corners: List<QRCornerType> = emptyList(),
    val selectedCorner: QRCornerType = QRCornerType.Square,
    val dots: List<QRDotType> = emptyList(),
    val selectedDot: QRDotType = QRDotType.Square,
    val showColorPicker: Boolean = false,
    val colorPickerType: ColorPickerType = ColorPickerType.PatternDotColor,
    val patternDotHex: String = "FF000000",
    val patternBackgroundHex: String = "FFFFFFFF",
    val frameHex: String = "FF000000",
    val frameDotHex: String = "FF000000",
    val logos: List<String> = emptyList(),
    val selectedLogo: String = "",
    val ownLogoPath: String? = null,
    val showPreview: Boolean = false
)
