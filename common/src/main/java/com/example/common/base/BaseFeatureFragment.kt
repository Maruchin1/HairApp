package com.example.common.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module

abstract class BaseFeatureFragment<VB : ViewBinding>(
    private val featureModule: Module
) : BaseFragment<VB>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(featureModule)
    }
}