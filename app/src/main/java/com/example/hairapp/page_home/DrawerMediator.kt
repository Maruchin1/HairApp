package com.example.hairapp.page_home

import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_main.*

class DrawerMediator(private val activity: MainActivity) {
    companion object {
        private const val DRAWER_GRAVITY = GravityCompat.START
    }

    init {
        activity.toolbar.onLeftAction = this::toggleDrawer
    }

    private fun toggleDrawer() {
        if (activity.drawer.isDrawerOpen(DRAWER_GRAVITY)) {
            activity.drawer.closeDrawer(DRAWER_GRAVITY)
        } else {
            activity.drawer.openDrawer(DRAWER_GRAVITY)
        }
    }

}