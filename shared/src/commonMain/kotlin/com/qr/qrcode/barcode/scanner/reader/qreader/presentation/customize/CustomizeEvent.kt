package com.qr.qrcode.barcode.scanner.reader.qreader.presentation.customize

import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRCornerMode
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRDotMode
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRPatternMode

sealed class CustomizeEvent {
    data class Customize(val state: CustomizeState) : CustomizeEvent()
    data class SelectPattern(val pattern: QRPatternMode) : CustomizeEvent()
    data class SelectCorner(val corner: QRCornerMode) : CustomizeEvent()
    data class SelectDot(val dot: QRDotMode) : CustomizeEvent()
    data class SelectColor(val hex: String) : CustomizeEvent()
    data class SelectLogo(val logo: String) : CustomizeEvent()

    data class ShowColorPicker(val colorPickerType: ColorPickerType) : CustomizeEvent()
    object DismissColorPicker : CustomizeEvent()

    data class ShowHidePreview(val show: Boolean) : CustomizeEvent()
}

enum class ColorPickerType {
    PatternDotColor,
    PatternBackgroundColor,
    FrameColor,
    FrameDotColor
}