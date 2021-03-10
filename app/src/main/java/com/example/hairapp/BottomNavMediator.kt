package com.example.hairapp

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.hairapp.page_cares_list.CaresListFragment
import com.example.hairapp.page_products_list.ProductsListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavMediator(
    bottomNav: BottomNavigationView,
    private val fragmentManager: FragmentManager
) {

    private val caresListFragment by lazy { CaresListFragment() }
    private val productsListFragment by lazy { ProductsListFragment() }

    init {
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_cares -> openCaresList()
                R.id.nav_products -> openProductsList()
            }
            true
        }
        bottomNav.selectedItemId = R.id.nav_cares
    }

    private fun openCaresList() = fragmentManager.commit {
        setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        replace(R.id.fragment_container, caresListFragment)
    }

    private fun openProductsList() = fragmentManager.commit {
        setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        replace(R.id.fragment_container, productsListFragment)
        addToBackStack(null)
    }
}