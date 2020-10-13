package com.example.hairapp.page_home

import android.view.MenuItem
import com.example.hairapp.R
import com.example.hairapp.page_care_schema.CareSchemaActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class DrawerNavigationMediator(private val activity: MainActivity): NavigationView.OnNavigationItemSelectedListener {

    init {
        activity.navigation.setNavigationItemSelectedListener(this)
    }

    private fun openCareSchema() {
        val intent = CareSchemaActivity.makeIntent(activity)
        activity.startActivity(intent)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.option_avg_peh -> Unit
            R.id.option_products_rating -> Unit
            R.id.option_photos_gallery -> Unit
            R.id.option_care_schema -> openCareSchema()
        }
        return false
    }
}