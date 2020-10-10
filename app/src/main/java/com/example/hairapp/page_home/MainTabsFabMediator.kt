package com.example.hairapp.page_home

import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainTabsFabMediator(private val activity: MainActivity) {

    init {
        activity.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) = showFab()
            override fun onTabUnselected(tab: TabLayout.Tab?) = hideFab()
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })
    }

    private fun showFab() {
        activity.lifecycleScope.launch {
            delay(300)
            activity.btn_add.show()
        }
    }

    private fun hideFab() {
        activity.btn_add.hide()
    }
}