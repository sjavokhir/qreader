package com.qr.qrcode.barcode.scanner.reader.qreader.android.core.helpers

import android.Manifest
import android.os.Build

const val cameraPermission = Manifest.permission.CAMERA

val locationPermissions: List<String>
    get() = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

val storagePermissions: List<String>
    get() = buildList {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.READ_MEDIA_IMAGES)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2) {
            add(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            add(Manifest.permission.READ_EXTERNAL_STORAGE)
            add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }