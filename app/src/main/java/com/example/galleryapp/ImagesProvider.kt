package com.example.galleryapp

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log

object ImagesProvider {
    private val TAG = "ImagesProvider"
    private val images = mutableListOf<Uri>()
    private lateinit var context: Context

    fun getImages(): List<Uri> {
        return images
    }

    fun init(context: Context) {
        this.context = context
        val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        loadFromContent(contentUri)
    }

    private fun loadFromContent(contentUri: Uri) {
        val content = getContentCursor(contentUri) ?: return
        var imageId: Long
        val columnIndexID: Int = content.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
        while (content.moveToNext()) {
            imageId = content.getLong(columnIndexID)
            Log.d(TAG, "imageId: $imageId")
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