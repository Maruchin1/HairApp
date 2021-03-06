package com.example.hairapp.framework

import android.view.ViewGroup
import androidx.transition.Fade
import androidx.transition.TransitionManager

object Animator {

    fun fastFade(sceneRoot: ViewGroup) {
        val fade = Fade().apply {
            duration = 200
        }
        TransitionManager.beginDelayedTransition(sceneRoot, fade)
    }
}