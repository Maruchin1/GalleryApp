package com.example.galleryapp

import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.ChangeTransform
import android.transition.TransitionSet

class PhotoTransition : TransitionSet() {
    init {
        ordering = ORDERING_TOGETHER

        addTransition(ChangeBounds())
            .addTransition(ChangeTransform())
            .addTransition(ChangeImageTransform())
    }
}