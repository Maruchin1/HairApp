package com.example.hairapp.page_care

import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_care.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CareTabsFabsMediator(private val activity: CareActivity) {

    init {
        activity.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) = showSelectedFab(tab)
            override fun onTabUnselected(tab: TabLayout.Tab?) = hideUnselectedTab(tab)
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })
    }

    private fun showSelectedFab(tab: TabLayout.Tab?) {
        when (tab?.position) {
            0 -> activity.lifecycleScope.launch {
                delay(300)
                activity.btn_add_step.show()
            }
            1 -> activity.lifecycleScope.launch {
                delay(300)
                activity.btn_add_photo.show()
            }
        }
    }

    private fun hideUnselectedTab(tab: TabLayout.Tab?) {
        when (tab?.position) {
            0 -> activity.btn_add_step.hide()
            1 -> activity.btn_add_photo.hide()
        }
    }
}