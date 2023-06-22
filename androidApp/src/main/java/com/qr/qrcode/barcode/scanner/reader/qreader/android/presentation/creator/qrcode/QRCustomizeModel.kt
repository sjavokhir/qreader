package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.qrcode

import android.net.Uri
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRCornerMode
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRDotMode
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRPatternMode
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.customize.CustomizeState
import java.io.Serializable

data class QRCustomizeModel(
    val selectedPattern: QRPatternMode = QRPatternMode.Square,
    val selectedCorner: QRCornerMode = QRCornerMode.Square,
    val selectedDot: QRDotMode = QRDotMode.Square,
    val patternDotHex: String = "FF000000",
    val patternBackgroundHex: String = "FFFFFFFF",
    val frameHex: String = "FF000000",
    val frameDotHex: String = "FF000000",
    val selectedLogo: String = "",
    val ownLogoPath: String? = null
) : Serializable

fun CustomizeState.toModel(uri: Uri? = null): QRCustomizeModel {
    return QRCustomizeModel(
        selectedPattern = selectedPattern,
        selectedCorner = selectedCorner,
        selectedDot = selectedDot,
        patternDotHex = patternDotHex,
        patternBackgroundHex = patternBackgroundHex,
        frameHex = frameHex,
        frameDotHex = frameDotHex,
        selectedLogo = selectedLogo,
        ownLogoPath = uri?.toString()
    )
}

fun QRCustomizeModel.toState(): CustomizeState {
    return CustomizeState(
        selectedPattern = selectedPattern,
        selectedCorner = selectedCorner,
        selectedDot = selectedDot,
        patternDotHex = patternDotHex,
        patternBackgroundHex = patternBackgroundHex,
        frameHex = frameHex,
        frameDotHex = frameDotHex,
        selectedLogo = selectedLogo,
        ownLogoPath = ownLogoPath
    )
}