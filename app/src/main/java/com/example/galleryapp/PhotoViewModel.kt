package com.example.galleryapp

import androidx.lifecycle.ViewModel

class PhotoViewModel : ViewModel() {

    private val photoIdList: List<Int> = listOf(
        R.drawable.bear,
        R.drawable.bi,
        R.drawable.color,
        R.drawable.doug,
        R.drawable.duck,
        R.drawable.ear,
        R.drawable.eyes,
        R.drawable.filin,
        R.drawable.golden,
        R.drawable.herons,
        R.drawable.khumakhod,
        R.drawable.leva,
        R.drawable.pause,
        R.drawable.pome,
        R.drawable.reward,
        R.drawable.shy,
        R.drawable.spla,
        R.drawable.sun,
        R.drawable.susp,
        R.drawable.turtle,
        R.drawable.vi_ol_et,
        R.drawable.w18,
        R.drawable.w23,
        R.drawable.w30,
        R.drawable.w33,
        R.drawable.w34
    )
    private var currPhotoPosition = 0

    fun getPhotoId(position: Int) = photoIdList[position]

    fun getPhotosCount() = photoIdList.size

    fun getCurrPhotoPosition() = currPhotoPosition

    fun setCurrPhotoPosition(newPosition: Int) {
        currPhotoPosition = newPosition
    }

    fun getCurrTransitionName() = "transition$currPhotoPosition"
}