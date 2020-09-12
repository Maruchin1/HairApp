package com.example.hairapp.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.hairapp.R
import com.example.hairapp.bind
import com.example.hairapp.setNavigationColor
import com.example.hairapp.setStatusBarColor
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind(R.layout.activity_main)
        setStatusBarColor(R.color.color_primary)
        setNavigationColor(R.color.color_white)
        setupTabs()
    }

    fun setupTabs() {
        tabs_pager.adapter = TabsAdapter()
        tabs.setupWithViewPager(tabs_pager)

        val careTab = tabs.getTabAt(TAB_CARE)
        careTab?.icon = ContextCompat.getDrawable(this, R.drawable.ic_round_bathtub_24)

        val productsTab = tabs.getTabAt(TAB_PRODUCTS)
        productsTab?.icon = ContextCompat.getDrawable(this, R.drawable.ic_round_list_24)
    }

    inner class TabsAdapter : FragmentStatePagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                TAB_CARE -> "Pielęgnacje"
                TAB_PRODUCTS -> "Produkty"
                else -> throw IllegalStateException("Tab index outside range (0, 1)")
            }
        }

        override fun getItem(position: Int): Fragment {
            return when (position) {
                TAB_CARE -> CareFragment()
                TAB_PRODUCTS -> ProductsFragment()
                else -> throw IllegalStateException("Tab index outside range (0, 1)")
            }
        }

        override fun getCount(): Int {
            return 2
        }
    }

    companion object {
        private const val TAB_CARE = 0
        private const val TAB_PRODUCTS = 1
    }
}