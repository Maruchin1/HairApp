package com.example.common.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module

abstract class BaseFeatureActivity<VB : ViewBinding>(
    private val featureModule: Module
) : BaseActivity<VB>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        loadKoinModules(featureModule)
        super.onCreate(savedInstanceState)
    }
}