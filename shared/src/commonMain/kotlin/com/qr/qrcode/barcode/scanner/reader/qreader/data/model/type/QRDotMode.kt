package com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type

enum class QRDotMode(
    val id: Int,
    val icon: String
) {
    Square(1, "ic_customize_dot_square"),
    Circle(2, "ic_customize_dot_circle"),
    Rounded(2, "ic_customize_dot_rounded")
}