package com.example.hairapp

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.hairapp.databinding.ActivityMainBinding

fun AppCompatActivity.setStatusBarColor(colorId: Int) {
    window.statusBarColor = ContextCompat.getColor(this, colorId)
}

fun AppCompatActivity.setNavigationColor(colorId: Int) {
    window.navigationBarColor = ContextCompat.getColor(this, colorId)
}

fun AppCompatActivity.bind(layoutId: Int) {
    DataBindingUtil.setContentView<ActivityMainBinding>(this, layoutId)
}