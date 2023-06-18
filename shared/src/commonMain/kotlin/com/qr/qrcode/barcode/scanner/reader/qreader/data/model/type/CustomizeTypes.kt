package com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type

enum class QRPatternType(
    val id: Int,
    val icon: String
) {
    Square(1, "ic_customize_pattern_square"),
    Rounded(2, "ic_customize_pattern_rounded"),
    Dots(3, "ic_customize_pattern_dots"),
    Classy(4, "ic_customize_pattern_classy"),
    ClassyRounded(5, "ic_customize_pattern_classy_rounded"),
    ExtraRounded(6, "ic_customize_pattern_extra_rounded")
}

enum class QRCornerType(
    val id: Int,
    val icon: String
) {
    NoStyle(1, ""),
    Square(2, "ic_customize_corner_square"),
    Rounded(3, "ic_customize_corner_rounded"),
    RoundedEdge(4, "ic_customize_corner_rounded_edge")
}

enum class QRDotType(
    val id: Int,
    val icon: String
) {
    NoStyle(1, ""),
    Square(2, "ic_customize_dots_square"),
    Rounded(3, "ic_customize_dots_rounded")
}