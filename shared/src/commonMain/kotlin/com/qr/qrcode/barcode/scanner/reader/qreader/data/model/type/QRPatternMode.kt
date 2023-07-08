package com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type

enum class QRPatternMode(
    val id: Int,
    val icon: String
) {
    Square(1, "ic_customize_pattern_square"),
    Rounded(2, "ic_customize_pattern_rounded"),
    Circle(3, "ic_customize_pattern_circle"),
    Classy(4, "ic_customize_pattern_classy"),
    ClassyRounded(5, "ic_customize_pattern_classy_rounded"),
    ExtraRounded(6, "ic_customize_pattern_extra_rounded")
}