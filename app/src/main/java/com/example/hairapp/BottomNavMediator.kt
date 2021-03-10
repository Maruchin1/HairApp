package com.example.hairapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.hairapp.page_cares_list.CaresListFragment
import com.example.hairapp.page_products_list.ProductsListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavMediator(
    bottomNav: BottomNavigationView,
    private val fragmentManager: FragmentManager
) {

    init {
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_cares -> openPage<CaresListFragment>()
                R.id.nav_products -> openPage<ProductsListFragment>()
            }
            true
        }
        bottomNav.selectedItemId = R.id.nav_cares
    }

    private inline fun <reified T : Fragment> openPage() {
        fragmentManager.commit {
            replace<T>(R.id.fragment_container)
            setReorderingAllowed(true)
        }
    }
}