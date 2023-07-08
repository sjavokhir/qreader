package com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type

enum class QRCornerMode(
    val id: Int,
    val icon: String
) {
    Square(1, "ic_customize_corner_square"),
    Circle(2, "ic_customize_corner_circle"),
    Rounded(3, "ic_customize_corner_rounded")
}