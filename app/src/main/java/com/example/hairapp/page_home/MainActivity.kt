package com.example.hairapp.page_home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.hairapp.R
import com.example.hairapp.framework.bindActivity
import com.example.hairapp.databinding.ActivityMainBinding
import com.example.hairapp.framework.Dialog
import com.example.hairapp.framework.setSystemColors
import com.example.hairapp.page_product_form.ProductFormActivity
import com.example.hairapp.page_care.CareActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding

    fun addNew() {
//        when (HomeTab.byPosition(tabs.selectedTabPosition)) {
//            HomeTab.CARE -> addNewCare()
//            HomeTab.PRODUCTS -> addNewProduct()
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindActivity(R.layout.activity_main, viewModel = null)
        setSystemColors(R.color.color_primary, R.color.color_primary_light)

        BottomNavMediator(binding.bottomNav, supportFragmentManager)
    }

    private fun addNewCare() = lifecycleScope.launch {
        val schemas = viewModel.getCareSchemas()
        Dialog.selectCareSchema(this@MainActivity, schemas)?.let { selectedSchema ->
            val intent = CareActivity.makeIntent(this@MainActivity, selectedSchema)
            startActivity(intent)
        }
    }

    private fun addNewProduct() {
        val intent = ProductFormActivity.makeIntent(this, null)
        startActivity(intent)
    }


}