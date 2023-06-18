package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.creator.qrcode

import android.graphics.drawable.Drawable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.github.alexzhirkevich.customqrgenerator.QrData
import com.github.alexzhirkevich.customqrgenerator.vector.QrCodeDrawable
import com.github.alexzhirkevich.customqrgenerator.vector.createQrVectorOptions
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorBallShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorFrameShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorPixelShape
import com.qr.qrcode.barcode.scanner.reader.qreader.core.extensions.tryCatch
import com.qr.qrcode.barcode.scanner.reader.qreader.shared.ioDispatcher
import kotlinx.coroutines.launch

@Composable
fun rememberQrDrawable(
    content: String
): Drawable? {
    val context = LocalContext.current

    var drawable by remember(content) {
        mutableStateOf<Drawable?>(null)
    }

    LaunchedEffect(drawable) {
        if (drawable != null) return@LaunchedEffect

        launch(ioDispatcher) {
            tryCatch {
                val data = QrData.Text(content)
                val options = createQrVectorOptions {
                    padding = .125f

//                    background {
//                        drawable = ContextCompat.getDrawable(context, R.drawable.frame)
//                    }
//
//                    logo {
//                        drawable = ContextCompat.getDrawable(context, R.drawable.logo)
//                        size = .25f
//                        padding = QrVectorLogoPadding.Natural(.2f)
//                        shape = QrVectorLogoShape.Circle
//                    }
//
//                    colors {
//                        dark = QrVectorColor.Solid(Color(0xff345288))
//                        ball = QrVectorColor.Solid(
//                            ContextCompat.getColor(context, R.color.your_color)
//                        )
//                    }

                    shapes {
                        darkPixel = QrVectorPixelShape.RoundCorners(.5f)
                        ball = QrVectorBallShape.RoundCorners(.25f)
                        frame = QrVectorFrameShape.RoundCorners(.25f)
                    }
                }

                drawable = QrCodeDrawable(data, options)
            }
        }
    }

    return remember(drawable) { drawable }
}