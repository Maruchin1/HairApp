package com.example.care_schema_details_dynamic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.care_schema_details_dynamic.databinding.ActivityCareSchemaDetailsBinding

class CareSchemaDetailsActivity : AppCompatActivity() {

    val toolbarTitle: LiveData<String> = liveData { emit("test dynamic") }

    private lateinit var binding: ActivityCareSchemaDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_care_schema_details)
        binding.lifecycleOwner = this
        binding.controler = this

        val view: View = binding.appbar.toolbar
    }
}