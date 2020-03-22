package com.example.galleryapp

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ImagesViewModel(
    private val images: List<Uri>
) : ViewModel() {

    private var currImagePosition = 0

    fun getImage(position: Int) = images[position]

    fun getImagesCount() = images.size

    fun getCurrImagePosition() = currImagePosition

    fun setCurrImagePosition(newPosition: Int) {
        currImagePosition = newPosition
    }

    fun getCurrTransitionName() = "transition$currImagePosition"

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val images = ImagesProvider.getImages()
            return ImagesViewModel(images) as T
        }
    }
}