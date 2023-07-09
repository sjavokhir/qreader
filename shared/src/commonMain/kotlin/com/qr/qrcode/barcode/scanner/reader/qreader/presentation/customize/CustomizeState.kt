package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.customize

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRCornerMode
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRDotMode
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRPatternMode

data class CustomizeState(
    val patterns: List<QRPatternMode> = emptyList(),
    val selectedPattern: QRPatternMode = QRPatternMode.Default,
    val corners: List<QRCornerMode> = emptyList(),
    val selectedCorner: QRCornerMode = QRCornerMode.Default,
    val dots: List<QRDotMode> = emptyList(),
    val selectedDot: QRDotMode = QRDotMode.Default,
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
