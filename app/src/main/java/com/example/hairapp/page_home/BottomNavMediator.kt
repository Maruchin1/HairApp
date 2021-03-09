package com.example.hairapp.page_home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.hairapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavMediator(
    bottomNav: BottomNavigationView,
    private val fragmentManager: FragmentManager
) {

    init {
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_cares -> openPage<CareListFragment>()
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