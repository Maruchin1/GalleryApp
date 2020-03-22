package com.example.galleryapp

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ImagesViewModel(
    private val images: List<Uri>
) : ViewModel() {

    private var currImagePosition = 0

    fun getImage(position: Int): Uri {
        checkPosition(position)
        return images[position]
    }

    fun getImagesCount() = images.size

    fun getCurrImagePosition() = currImagePosition

    fun setCurrImagePosition(newPosition: Int) {
        checkPosition(newPosition)
        currImagePosition = newPosition
    }

    private fun checkPosition(position: Int) {
        val isPositionCorrect = position in images.indices
        if (!isPositionCorrect) {
            throw IncorrectPosition()
        }
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val images = ImagesProvider(context).getImages()
            return ImagesViewModel(images) as T
        }
    }

    class IncorrectPosition : Exception()
}