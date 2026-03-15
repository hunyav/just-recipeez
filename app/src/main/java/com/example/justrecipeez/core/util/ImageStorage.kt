package com.example.justrecipeez.core.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

object ImageStorage {
    fun saveBitmap(context: Context, bitmap: Bitmap): String {
        val file = createImageFile(context)
        FileOutputStream(file).use { output ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, output)
        }
        return Uri.fromFile(file).toString()
    }

    private fun createImageFile(context: Context): File {
        val dir = File(context.filesDir, "recipe-images").apply { mkdirs() }
        return File(dir, "${UUID.randomUUID()}.jpg")
    }
}
