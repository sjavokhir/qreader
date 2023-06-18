package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.customize

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRCornerType
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRDotType
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRPatternType

sealed class CustomizeEvent {
    data class SelectPattern(val pattern: QRPatternType) : CustomizeEvent()
    data class SelectCorner(val corner: QRCornerType) : CustomizeEvent()
    data class SelectDot(val dot: QRDotType) : CustomizeEvent()
    data class SelectColor(val hex: String) : CustomizeEvent()
    data class SelectLogo(val logo: String) : CustomizeEvent()

    data class ShowColorPicker(val colorPickerType: ColorPickerType) : CustomizeEvent()
    object DismissColorPicker : CustomizeEvent()

    data class ShowHidePreview(val show: Boolean): CustomizeEvent()
}

enum class ColorPickerType {
    PatternDotColor,
    PatternBackgroundColor,
    CornerColor,
    CornerDotColor
}