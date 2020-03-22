package com.example.galleryapp

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore

class ImagesProvider(
    private val context: Context
) {
    private val images = mutableListOf<Uri>()

    init {
        val internalContentUri = MediaStore.Images.Media.INTERNAL_CONTENT_URI
        val externalContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        loadFromContent(internalContentUri)
        loadFromContent(externalContentUri)
    }

    fun getImages(): List<Uri> {
        return images
    }

    private fun loadFromContent(contentUri: Uri) {
        val content = getContentCursor(contentUri) ?: return
        var imageId: Long
        val columnIndexID: Int = content.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
        while (content.moveToNext()) {
            imageId = content.getLong(columnIndexID)
            val uriImage = Uri.withAppendedPath(contentUri, "" + imageId)
            images.add(uriImage)
        }
        content.close()
    }

    private fun getContentCursor(contentUri: Uri): Cursor? {
        val projection = arrayOf(MediaStore.Images.Media._ID)
        return context.contentResolver.query(contentUri, projection, null, null, null)
    }
}