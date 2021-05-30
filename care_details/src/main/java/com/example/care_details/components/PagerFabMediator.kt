package com.example.care_details.components

import androidx.viewpager.widget.ViewPager
import com.example.care_details.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

internal class PagerFabMediator(
    private val fab: FloatingActionButton,
    private val pager: ViewPager
) : ViewPager.SimpleOnPageChangeListener() {

    init {
        pager.addOnPageChangeListener(this)
    }

    override fun onPageSelected(position: Int) {
        fab.hide()
        getFabIconForPosition(position)?.let { iconResId ->
            fab.setImageResource(iconResId)
            fab.show()
        }
    }

    private fun getFabIconForPosition(position: Int): Int? {
        return when (position) {
            0 -> R.drawable.ic_round_add_24
            1 -> R.drawable.ic_round_add_a_photo_24
            else -> null
        }
    }
}