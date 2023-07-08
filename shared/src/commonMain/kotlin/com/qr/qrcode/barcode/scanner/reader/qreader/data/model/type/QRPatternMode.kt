package com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type

enum class QRPatternMode(
    val id: Int,
    val icon: String
) {
    Square(1, "ic_customize_pattern_square"),
    Rounded(2, "ic_customize_pattern_rounded"),
    ExtraRounded(3, "ic_customize_pattern_extra_rounded"),
    Circle(4, "ic_customize_pattern_circle"),
    CirclePadding(5, "ic_customize_pattern_circle_padding"),
    Horizontal(6, "ic_customize_pattern_horizontal"),
    Vertical(7, "ic_customize_pattern_vertical"),
    Rhombus(8, "ic_customize_pattern_rhombus"),
    Star(9, "ic_customize_pattern_star"),
    Classy(10, "ic_customize_pattern_classy"),
    ClassyRounded(11, "ic_customize_pattern_classy_rounded"),
}