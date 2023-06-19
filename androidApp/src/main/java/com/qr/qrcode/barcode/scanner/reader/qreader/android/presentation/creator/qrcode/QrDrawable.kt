package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.qrcode

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.github.alexzhirkevich.customqrgenerator.QrData
import com.github.alexzhirkevich.customqrgenerator.vector.QrCodeDrawable
import com.github.alexzhirkevich.customqrgenerator.vector.createQrVectorOptions
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorBallShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorColor
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorFrameShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogoPadding
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogoShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorPixelShape
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.drawableId
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRCornerType
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRDotType
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRPatternType

@Composable
fun rememberQrDrawable(
    content: String,
    patternType: QRPatternType = QRPatternType.Square,
    cornerType: QRCornerType = QRCornerType.Square,
    dotType: QRDotType = QRDotType.Square,
    patternDotHex: String = "FF000000",
    patternBackgroundHex: String = "FFFFFFFF",
    frameHex: String = "FF000000",
    frameDotHex: String = "FF000000",
    selectedLogo: String = ""
): Drawable {
    val context = LocalContext.current

    val data = QrData.Text(content)
    val options = createQrVectorOptions {
        padding = .15f

        background {
            color = QrVectorColor.Solid(Color.parseColor("#$patternBackgroundHex"))
        }

        if (selectedLogo.isNotEmpty()) {
            context.drawableId(selectedLogo)?.let {
                logo {
                    drawable = ContextCompat.getDrawable(context, it)
                    size = .25f
                    padding = QrVectorLogoPadding.Natural(.2f)
                    shape = QrVectorLogoShape.Default
                }
            }
        }

        colors {
            dark = QrVectorColor.Solid(Color.parseColor("#$patternDotHex"))
            frame = QrVectorColor.Solid(Color.parseColor("#$frameHex"))
            ball = QrVectorColor.Solid(Color.parseColor("#$frameDotHex"))
        }

        shapes {
            darkPixel = when (patternType) {
                QRPatternType.Square -> QrVectorPixelShape.RoundCorners(0f)
                QRPatternType.Rounded -> QrVectorPixelShape.RoundCorners(.25f)
                QRPatternType.Circle -> QrVectorPixelShape.Circle()
                QRPatternType.Classy -> QrVectorPixelShape.Rhombus()
                QRPatternType.ClassyRounded -> QrVectorPixelShape.Star
                QRPatternType.ExtraRounded -> QrVectorPixelShape.RoundCorners(.5f)
            }
            frame = when (cornerType) {
                QRCornerType.Square -> QrVectorFrameShape.RoundCorners(0f)
                QRCornerType.Circle -> QrVectorFrameShape.Circle()
                QRCornerType.Rounded -> QrVectorFrameShape.RoundCorners(.25f)
            }
            ball = when (dotType) {
                QRDotType.Square -> QrVectorBallShape.RoundCorners(0f)
                QRDotType.Circle -> QrVectorBallShape.Circle(1f)
                QRDotType.Rounded -> QrVectorBallShape.RoundCorners(.25f)
            }
        }
    }

    return QrCodeDrawable(data, options)
}