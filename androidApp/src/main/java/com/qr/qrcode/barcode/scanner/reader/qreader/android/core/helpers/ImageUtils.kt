package com.qr.qrcode.barcode.scanner.reader.qreader.android.core.helpers

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import androidx.core.graphics.drawable.toBitmap
import com.qr.qrcode.barcode.scanner.reader.qreader.android.R
import com.qr.qrcode.barcode.scanner.reader.qreader.android.core.extensions.toast
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object ImageUtils {

    fun saveDrawableToGallery(context: Context, drawable: Drawable) {
        val bitmap = drawable.toBitmap(1024, 1024)
        saveBitmapToGallery(context, bitmap)
    }

    fun shareDrawable(context: Context, drawable: Drawable) {
        val bitmap = drawable.toBitmap(1024, 1024)
        val uri = saveBitmapToGallery(context, bitmap, false)

        // Grant temporary permissions to access the file URI
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().build())

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/jpeg"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share Photo"))
    }

    private fun saveBitmapToGallery(
        context: Context,
        bitmap: Bitmap,
        showMessage: Boolean = true
    ): Uri {
        val title = "QR_${System.currentTimeMillis()}"

        // Get the image's directory
        val imageDirectory = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        )
        val imageFile = File(imageDirectory, "$title.jpg")

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, title)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.DATA, imageFile.absolutePath)
        }

        var outputStream: OutputStream? = null
        try {
            outputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()

            // Insert image into the MediaStore
            context.contentResolver.apply {
                insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            }

            // Notify the gallery about the new image
            MediaScannerConnection.scanFile(
                context,
                arrayOf(imageFile.absolutePath),
                null,
                null
            )

            if (showMessage) {
                context.toast(context.getString(R.string.image_saved_to_gallery))
            }
        } catch (_: Throwable) {
            if (showMessage) {
                context.toast(context.getString(R.string.failed_to_save_image))
            }
        } finally {
            outputStream?.close()
        }

        return Uri.fromFile(imageFile)
    }

    fun getDrawableFromUri(context: Context, uri: Uri?): Drawable? {
        uri ?: return null

        try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            return BitmapDrawable(context.resources, bitmap)
        } catch (_: Throwable) {
        }
        return null
    }

    fun getDrawableFromPath(context: Context, path: String?): Drawable? {
        path ?: return null

        return getDrawableFromUri(context, Uri.parse(path))
    }
}
