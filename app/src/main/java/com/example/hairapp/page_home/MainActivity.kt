package com.example.hairapp.page_home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.lifecycleScope
import com.example.hairapp.R
import com.example.hairapp.framework.bind
import com.example.hairapp.databinding.ActivityMainBinding
import com.example.hairapp.framework.Dialog
import com.example.hairapp.framework.setSystemColors
import com.example.hairapp.page_product_form.ProductFormActivity
import com.example.hairapp.page_care.CareActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModel()

    fun addNew() {
        when (HomeTab.byPosition(tabs.selectedTabPosition)) {
            HomeTab.CARE -> addNewCare()
            HomeTab.PRODUCTS -> addNewProduct()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind<ActivityMainBinding>(R.layout.activity_main, viewModel = null)
        setSystemColors(R.color.color_primary)
        setupTabs()
        DrawerMediator(this)
        DrawerNavigationMediator(this)
    }

    private fun setupTabs() {
        tabs_pager.adapter = TabsAdapter()
        tabs.setupWithViewPager(tabs_pager)

        val careTab = tabs.getTabAt(HomeTab.CARE.position)
        careTab?.icon = ContextCompat.getDrawable(this, R.drawable.ic_round_bathtub_24)

        val productsTab = tabs.getTabAt(HomeTab.PRODUCTS.position)
        productsTab?.icon = ContextCompat.getDrawable(this, R.drawable.ic_round_shopping_basket_24)

        MainTabsFabMediator(this)
    }

    private fun addNewCare() = lifecycleScope.launch {
        val schemas = viewModel.getCareSchemas()
        Dialog.selectCareSchema(this@MainActivity, schemas)?.let { selectedSchema ->
            val intent = CareActivity.makeIntent(this@MainActivity, selectedSchema)
            startActivity(intent)
        }
    }

    private fun addNewProduct() {
        val intent = ProductFormActivity.makeIntent(this, null)
        startActivity(intent)
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
                HomeTab.CARE -> CareListFragment()
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