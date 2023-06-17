package com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type

enum class QRFrameType {
    NoFrame,
    Frame1,
    Frame2,
    Frame3,
    Frame4,
    Frame5,
}

enum class QRPatternType {
    Square,
    Rounded,
    Dots,
    Classy,
    ClassyRounded,
    ExtraRounded,
}

enum class QRCornerStyleType {
    NoStyle,
    Square,
    Rounded,
    RoundEdgedSquare
}

enum class QRCornerDotsType {
    NoStyle,
    RoundDot,
    SquareDot
}