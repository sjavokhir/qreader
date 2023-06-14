package com.qr.qrcode.barcode.scanner.reader.qreader.android.presentation.scanner

import android.annotation.SuppressLint
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.qr.qrcode.barcode.scanner.reader.qreader.android.camera.detector.QRDetector
import com.qr.qrcode.barcode.scanner.reader.qreader.core.helpers.tryCatch
import java.util.*
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private val executor = Executors.newSingleThreadExecutor()

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraView(
    modifier: Modifier,
    isFlashlightOn: Boolean,
    onResult: () -> Unit
) {
    val cameraPermissionState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    if (cameraPermissionState.allPermissionsGranted) {
        CameraWithGrantedPermission(
            modifier = modifier,
            isFlashlightOn = isFlashlightOn,
            onResult = onResult
        )
    } else {
        LaunchedEffect(Unit) {
            cameraPermissionState.launchMultiplePermissionRequest()
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
private fun CameraWithGrantedPermission(
    modifier: Modifier,
    isFlashlightOn: Boolean,
    onResult: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var camera by remember { mutableStateOf<Camera?>(null) }

    val preview = remember {
        Preview.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_4_3)
            .build()
    }
    val previewView = remember { PreviewView(context) }
    val imageAnalysis = remember {
        ImageAnalysis.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_4_3)
            .build()
    }
    val cameraSelector = remember {
        CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
    }
    val detector = remember { QRDetector(context, onResult) }

    LaunchedEffect(Unit) {
        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(context)) {
            tryCatch {
                detector.processImageProxy(it)
            }
        }

        val cameraProvider = suspendCoroutine<ProcessCameraProvider> { continuation ->
            ProcessCameraProvider.getInstance(context).also { cameraProvider ->
                cameraProvider.addListener({
                    continuation.resume(cameraProvider.get())
                }, executor)
            }
        }

        cameraProvider.unbindAll()
        camera = cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageAnalysis
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    LaunchedEffect(isFlashlightOn) {
        camera?.cameraControl?.enableTorch(isFlashlightOn)
    }

    Box(
        modifier = modifier
    ) {
        AndroidView(
            { previewView },
            modifier = Modifier.fillMaxSize()
        )
    }
}