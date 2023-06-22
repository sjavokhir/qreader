package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.qrcode

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRCornerMode
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRDotMode
import com.qr.qrcode.barcode.scanner.reader.qreader.data.model.type.QRPatternMode

@Composable
fun rememberQrDrawable(
    content: String,
    model: QRCustomizeModel,
    ownLogo: Drawable? = null
): Drawable {
    val context = LocalContext.current

    val qrOptions = remember(content, model, ownLogo) {
        createQrVectorOptions {
            padding = .15f

            background {
                color = QrVectorColor.Solid(Color.parseColor("#${model.patternBackgroundHex}"))
            }

            if (model.selectedLogo.isNotEmpty()) {
                context.drawableId(model.selectedLogo)?.let {
                    logo {
                        drawable = ContextCompat.getDrawable(context, it)
                        size = .25f
                        padding = QrVectorLogoPadding.Natural(.2f)
                        shape = QrVectorLogoShape.Default
                    }
                }
            } else if (ownLogo != null) {
                logo {
                    drawable = ownLogo
                    size = .25f
                    padding = QrVectorLogoPadding.Natural(.2f)
                    shape = QrVectorLogoShape.Default
                }
            }

            colors {
                dark = QrVectorColor.Solid(Color.parseColor("#${model.patternDotHex}"))
                frame = QrVectorColor.Solid(Color.parseColor("#${model.frameHex}"))
                ball = QrVectorColor.Solid(Color.parseColor("#${model.frameDotHex}"))
            }

            shapes {
                darkPixel = when (model.selectedPattern) {
                    QRPatternMode.Square -> QrVectorPixelShape.RoundCorners(0f)
                    QRPatternMode.Rounded -> QrVectorPixelShape.RoundCorners(.25f)
                    QRPatternMode.Circle -> QrVectorPixelShape.Circle()
                    QRPatternMode.Classy -> QrVectorPixelShape.Rhombus()
                    QRPatternMode.ClassyRounded -> QrVectorPixelShape.Star
                    QRPatternMode.ExtraRounded -> QrVectorPixelShape.RoundCorners(.5f)
                }
                frame = when (model.selectedCorner) {
                    QRCornerMode.Square -> QrVectorFrameShape.RoundCorners(0f)
                    QRCornerMode.Circle -> QrVectorFrameShape.Circle()
                    QRCornerMode.Rounded -> QrVectorFrameShape.RoundCorners(.25f)
                }
                ball = when (model.selectedDot) {
                    QRDotMode.Square -> QrVectorBallShape.RoundCorners(0f)
                    QRDotMode.Circle -> QrVectorBallShape.Circle(1f)
                    QRDotMode.Rounded -> QrVectorBallShape.RoundCorners(.25f)
                }
            }
        }
    }

    val qrDrawable by remember(content, model, qrOptions) {
        mutableStateOf(QrCodeDrawable(QrData.Text(content), qrOptions))
    }

    return qrDrawable
}