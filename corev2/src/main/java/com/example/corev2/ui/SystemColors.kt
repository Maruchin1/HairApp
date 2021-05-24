package com.example.corev2.ui

import android.app.Activity
import android.view.View
import com.example.corev2.R

class SystemColors(private val activity: Activity) {

    private var statusBarColor: PossibleColor? = null
    private var navigationBarColor: PossibleColor? = null

    fun allLight(): SystemColors {
        return lightStatusBar().lightNavigationBar()
    }

    fun allDark(): SystemColors {
        return darkStatusBar().darkNavigationBar()
    }

    fun allBlack(): SystemColors {
        return blackStatusBar().blackNavigationBar()
    }

    fun allTransparent(): SystemColors {
        return transparentStatusBar().transparentNavigationBar()
    }

    fun lightStatusBar(): SystemColors {
        statusBarColor = PossibleColor.LIGHT
        return this
    }

    fun lightNavigationBar(): SystemColors {
        navigationBarColor = PossibleColor.LIGHT
        return this
    }

    fun darkStatusBar(): SystemColors {
        statusBarColor = PossibleColor.DARK
        return this
    }

    fun darkNavigationBar(): SystemColors {
        navigationBarColor = PossibleColor.DARK
        return this
    }

    fun blackStatusBar(): SystemColors {
        statusBarColor = PossibleColor.BLACK
        return this
    }

    fun blackNavigationBar(): SystemColors {
        navigationBarColor = PossibleColor.BLACK
        return this
    }

    fun transparentStatusBar(): SystemColors {
        statusBarColor = PossibleColor.TRANSPARENT
        return this
    }

    fun transparentNavigationBar(): SystemColors {
        navigationBarColor = PossibleColor.TRANSPARENT
        return this
    }

    internal fun apply() {
        statusBarColor?.let {
            setStatusBarColor(it.colorResId)
            setUiVisibility(it, View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }
        navigationBarColor?.let {
            setNavigationBarColor(it.colorResId)
            setUiVisibility(it, View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
        }
    }

    private fun setStatusBarColor(colorId: Int) {
        activity.window.statusBarColor = activity.getColor(colorId)
    }

    private fun setNavigationBarColor(colorId: Int) {
        activity.window.navigationBarColor = activity.getColor(colorId)
    }

    private fun setUiVisibility(color: PossibleColor, flag: Int) {
        val isLight = color in arrayOf(PossibleColor.LIGHT, PossibleColor.TRANSPARENT)
        val currentFlags = activity.window.decorView.systemUiVisibility
        activity.window.decorView.systemUiVisibility = if (isLight) {
            currentFlags or flag
        } else {
            currentFlags and flag.inv()
        }
    }

    private enum class PossibleColor(val colorResId: Int) {
        LIGHT(R.color.color_background),
        DARK(R.color.color_primary),
        BLACK(R.color.color_black),
        TRANSPARENT(R.color.color_transparent)
    }
}