package com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type

enum class QRDotMode(
    val id: Int,
    val icon: String
) {
    Square(1, "ic_customize_dot_square"),
    Rounded(2, "ic_customize_dot_rounded"),
    Circle(3, "ic_customize_dot_circle"),
    Rhombus(4, "ic_customize_dot_rhombus"),
    CornerTwo(5, "ic_customize_dot_two"),
    CornerThree(6, "ic_customize_dot_three"),
    Dots(7, "ic_customize_dot_dots"),
    DotsPadding(8, "ic_customize_dot_dots_padding"),
    Horizontal(9, "ic_customize_dot_horizontal"),
    Vertical(10, "ic_customize_dot_vertical")
}