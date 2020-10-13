package com.example.hairapp.page_home

import android.view.MenuItem
import com.example.hairapp.R
import com.example.hairapp.page_care_schema.CareSchemaActivity
import com.example.hairapp.page_photos_gallery.PhotosGalleryActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class DrawerNavigationMediator(private val activity: MainActivity): NavigationView.OnNavigationItemSelectedListener {

    init {
        activity.navigation.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.option_avg_peh -> Unit
            R.id.option_products_rating -> Unit
            R.id.option_photos_gallery -> openPhotosGallery()
            R.id.option_care_schema -> openCareSchema()
        }
        return true
    }

    private fun openPhotosGallery() {
        val intent = PhotosGalleryActivity.makeIntent(activity)
        activity.startActivity(intent)
    }

    private fun openCareSchema() {
        val intent = CareSchemaActivity.makeIntent(activity)
        activity.startActivity(intent)
    }
}