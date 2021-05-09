package com.example.common.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module

abstract class BaseFeatureActivity<T : ViewDataBinding>(
    private val featureModule: Module,
    layoutId: Int
) : BaseActivity<T>(layoutId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        loadKoinModules(featureModule)
        super.onCreate(savedInstanceState)
    }
}