package com.example.care_details.components

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.care_details.R
import com.example.corev2.navigation.CareDetailsDestination
import com.example.corev2.navigation.Destination

class CareDetailsActivity : AppCompatActivity() {

    private val params: CareDetailsDestination.Params?
        get() = intent.getParcelableExtra(Destination.EXTRA_PARAMS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_care_details)
    }
}