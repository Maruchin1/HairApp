package com.example.hairapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.example.hairapp.page_cares_list.CaresListFragment
import com.example.hairapp.page_products_list.ProductsListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavMediator(
    private val bottomNav: BottomNavigationView,
    private val fragmentManager: FragmentManager
) {

    private val caresListFragment by lazy { CaresListFragment() }
    private val productsListFragment by lazy { ProductsListFragment() }

    init {
        listenForNavigationSelected()
        listenForBackEvents()
        setDefaultPage()
    }

    private fun listenForNavigationSelected() {
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_cares -> openCaresList()
                R.id.nav_products -> openProductsList()
            }
            true
        }
    }

    private fun openCaresList() = fragmentManager.commit {
        setFadeAnimations()
        replace(caresListFragment)
    }

    private fun openProductsList() = fragmentManager.commit {
        setFadeAnimations()
        replace(productsListFragment)
        addToBackStack()
    }

    private fun FragmentTransaction.setFadeAnimations() {
        setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
    }

    private fun FragmentTransaction.replace(fragment: Fragment) {
        replace(R.id.fragment_container, fragment)
    }

    private fun FragmentTransaction.addToBackStack() {
        addToBackStack(null)
    }

    private fun listenForBackEvents() {
        var lastNumOfFragments = 0
        fragmentManager.addOnBackStackChangedListener {
            val newNumOfFragments = fragmentManager.backStackEntryCount
            if (newNumOfFragments < lastNumOfFragments) {
                setBottomNavByVisibleFragment()
            }
            lastNumOfFragments = newNumOfFragments
        }
    }

    private fun setBottomNavByVisibleFragment() {
        fragmentManager.fragments
            .find { it.isVisible }
            ?.let { setBottomNavByFragment(it) }
    }

    private fun setBottomNavByFragment(fragment: Fragment) {
        bottomNav.selectedItemId = when (fragment) {
            is CaresListFragment -> R.id.nav_cares
            is ProductsListFragment -> R.id.nav_products
            else -> -1
        }
    }

    private fun setDefaultPage() {
        bottomNav.selectedItemId = R.id.nav_cares
    }
}