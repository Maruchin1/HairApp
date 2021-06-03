package com.example.care_details.components

import androidx.viewpager.widget.ViewPager
import com.example.care_details.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

internal class PagerFabMediator(
    private val fab: FloatingActionButton,
    private val pager: ViewPager,
    private val onAddStepClicked: () -> Unit,
    private val onAddPhotoClicked: () -> Unit
) : ViewPager.SimpleOnPageChangeListener() {

    private var currentPosition = 0

    init {
        pager.addOnPageChangeListener(this)
        setupFabOnClickClickListener()
    }

    override fun onPageSelected(position: Int) {
        currentPosition = position
        fab.hide()
        getFabIconForCurrentPosition()?.let { iconResId ->
            fab.setImageResource(iconResId)
            fab.show()
        }
    }

    private fun getFabIconForCurrentPosition(): Int? {
        return when (currentPosition) {
            0 -> R.drawable.ic_round_add_24
            1 -> R.drawable.ic_round_add_a_photo_24
            else -> null
        }
    }

    private fun setupFabOnClickClickListener() = fab.setOnClickListener {
        when (currentPosition) {
            0 -> onAddStepClicked()
            1 -> onAddPhotoClicked()
        }
    }
}