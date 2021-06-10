package com.example.product_details.ui

import android.animation.ValueAnimator
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.product_details.R

private const val ANIMATION_DURATION = 300L

private val ProductDetailsActivity.displayColor
    get() = getColor(R.color.color_background)

private val ProductDetailsActivity.editColor
    get() = getColor(R.color.color_primary)

internal fun ProductDetailsActivity.animateStartEdition() {
    val transition = AutoTransition().apply {
        duration = ANIMATION_DURATION
    }
    TransitionManager.beginDelayedTransition(binding.root, transition)
}

internal fun ProductDetailsActivity.animateFinishEdition() {
    val transition = AutoTransition().apply {
        duration = ANIMATION_DURATION
    }
    TransitionManager.beginDelayedTransition(binding.root, transition)
}

internal fun ProductDetailsActivity.animateStatusBarToEditColor() {
    ValueAnimator.ofArgb(displayColor, editColor).apply {
        startDelay = 2 * ANIMATION_DURATION
        duration = ANIMATION_DURATION
        addUpdateListener { window.statusBarColor = it.animatedValue as Int }
    }.start()
}

internal fun ProductDetailsActivity.animateStatusBarToDisplayColor() {
    ValueAnimator.ofArgb(editColor, displayColor).apply {
        duration = ANIMATION_DURATION
        addUpdateListener { window.statusBarColor = it.animatedValue as Int }
    }.start()
}