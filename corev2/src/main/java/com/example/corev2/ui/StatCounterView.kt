package com.example.corev2.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.corev2.databinding.ViewStatCounterBinding

class StatCounterView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private val binding = ViewStatCounterBinding.inflate(LayoutInflater.from(context), this, true)

}