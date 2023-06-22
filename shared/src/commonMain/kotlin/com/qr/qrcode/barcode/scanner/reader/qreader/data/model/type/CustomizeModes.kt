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

enum class QRCornerMode(
    val id: Int,
    val icon: String
) {
    Square(1, "ic_customize_corner_square"),
    Circle(2, "ic_customize_corner_circle"),
    Rounded(3, "ic_customize_corner_rounded")
}

enum class QRDotMode(
    val id: Int,
    val icon: String
) {
    Square(1, "ic_customize_dot_square"),
    Circle(2, "ic_customize_dot_circle"),
    Rounded(2, "ic_customize_dot_rounded")
}