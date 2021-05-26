package com.example.corev2.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected abstract val inflateBinding: InflateActivityBinding<VB>

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateBinding(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        SystemColors(this).run {
            setupSystemColors(this)
            apply()
        }
    }

    protected abstract fun setupSystemColors(systemColors: SystemColors)
}