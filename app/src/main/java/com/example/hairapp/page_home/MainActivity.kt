package com.example.hairapp.page_home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.hairapp.R
import com.example.hairapp.framework.bind
import com.example.hairapp.databinding.ActivityMainBinding
import com.example.hairapp.page_product_form.ProductFormActivity
import com.example.hairapp.framework.setNavigationColor
import com.example.hairapp.framework.setStatusBarColor
import com.example.hairapp.page_care_form.CareFormActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    fun addNew() {
        val intent = when (HomeTab.byPosition(tabs.selectedTabPosition)) {
            HomeTab.CARE -> CareFormActivity.makeIntent(this)
            HomeTab.PRODUCTS -> ProductFormActivity.makeIntent(this, null)
        }
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind<ActivityMainBinding>(R.layout.activity_main, viewModel = null)
        setStatusBarColor(R.color.color_primary)
        setNavigationColor(R.color.color_white)
        setupTabs()
    }

    private fun setupTabs() {
        tabs_pager.adapter = TabsAdapter()
        tabs.setupWithViewPager(tabs_pager)

        val careTab = tabs.getTabAt(HomeTab.CARE.position)
        careTab?.icon = ContextCompat.getDrawable(this, R.drawable.ic_round_bathtub_24)

        val productsTab = tabs.getTabAt(HomeTab.PRODUCTS.position)
        productsTab?.icon = ContextCompat.getDrawable(this, R.drawable.ic_round_shopping_basket_24)
    }

    inner class TabsAdapter : FragmentStatePagerAdapter(
        supportFragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
        override fun getPageTitle(position: Int): CharSequence? {
            return when (HomeTab.byPosition(position)) {
                HomeTab.CARE -> "Pielęgnacje"
                HomeTab.PRODUCTS -> "Produkty"
            }
        }

        override fun getItem(position: Int): Fragment {
            return when (HomeTab.byPosition(position)) {
                HomeTab.CARE -> CareFragment()
                HomeTab.PRODUCTS -> ProductsListFragment()
            }
        }

        override fun getCount(): Int {
            return 2
        }
    }

    private enum class HomeTab(val position: Int) {
        CARE(0),
        PRODUCTS(1);

        companion object {
            fun byPosition(position: Int) = when (position) {
                0 -> CARE
                1 -> PRODUCTS
                else -> throw IllegalStateException("Tab number has to be 0 (care) or 1 (products)")
            }
        }
    }
}