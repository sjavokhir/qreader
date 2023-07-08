package com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type

enum class QRCornerMode(
    val id: Int,
    val icon: String
) {
    Square(1, "ic_customize_corner_square"),
    Rounded(2, "ic_customize_corner_rounded"),
    Circle(3, "ic_customize_corner_circle"),
    Two(4, "ic_customize_corner_two"),
    Three(5, "ic_customize_corner_three"),
    Dots(6, "ic_customize_corner_dots"),
    DotsPadding(7, "ic_customize_corner_dots_padding"),
    Rhombus(7, "ic_customize_corner_rhombus"),
}