package com.example.hairapp.home

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.transition.ChangeBounds
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.example.hairapp.R
import kotlinx.android.synthetic.main.view_home_tabs.view.*

class HomeTabsView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private val _selectedTab = MutableLiveData(CARE_TAB)

    val selectedTab: LiveData<Int> = _selectedTab

    init {
        View.inflate(context, R.layout.view_home_tabs, this)
        home_tabs_tab_care.setOnClickListener { selectCare() }
        home_tabs_tab_products.setOnClickListener { selectProducts() }
        home_tabs_care_selected_indicator.visibility = View.VISIBLE
        home_tabs_products_selected_indicator.visibility = View.INVISIBLE
    }

    private fun selectCare() {
        if (_selectedTab.value != CARE_TAB) {
            _selectedTab.value = CARE_TAB
            runTransition()
            home_tabs_products_selected_indicator.visibility = View.INVISIBLE
            home_tabs_care_selected_indicator.visibility = View.VISIBLE
        }
    }

    private fun selectProducts() {
        if (_selectedTab.value != PRODUCTS_TAB) {
            _selectedTab.value = PRODUCTS_TAB
            runTransition()
            home_tabs_care_selected_indicator.visibility = View.INVISIBLE
            home_tabs_products_selected_indicator.visibility = View.VISIBLE
        }
    }

    private fun runTransition() {
        val careTransition = Slide(Gravity.END).apply { duration = 200 }
        val productsTransition = Slide(Gravity.START).apply { duration = 200 }
        TransitionManager.beginDelayedTransition(home_tabs_tab_products, productsTransition)
        TransitionManager.beginDelayedTransition(home_tabs_tab_care, careTransition)
    }

    companion object {
        const val CARE_TAB = 0
        const val PRODUCTS_TAB = 1
    }
}