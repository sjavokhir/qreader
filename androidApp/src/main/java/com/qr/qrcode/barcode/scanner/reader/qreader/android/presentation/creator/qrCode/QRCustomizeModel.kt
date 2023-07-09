package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.qrCode

import android.net.Uri
import com.qr.qrcode.barcode.scanner.reader.qreader.data.database.entity.HistoryEntity
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRCornerMode
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRDotMode
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRPatternMode
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.toQRCornerMode
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.toQRDotMode
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.toQRPatternMode
import com.qr.qrcode.barcode.scanner.reader.qreader.presentation.customize.CustomizeState
import java.io.Serializable

data class QRCustomizeModel(
    val selectedPattern: QRPatternMode = QRPatternMode.Default,
    val selectedCorner: QRCornerMode = QRCornerMode.Default,
    val selectedDot: QRDotMode = QRDotMode.Default,
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

fun HistoryEntity.toModel(): QRCustomizeModel {
    return QRCustomizeModel(
        selectedPattern = selectedPattern.toQRPatternMode(),
        selectedCorner = selectedCorner.toQRCornerMode(),
        selectedDot = selectedDot.toQRDotMode(),
        patternDotHex = patternDotHex,
        patternBackgroundHex = patternBackgroundHex,
        frameHex = frameHex,
        frameDotHex = frameDotHex,
        selectedLogo = selectedLogo,
        ownLogoPath = ownLogoPath
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