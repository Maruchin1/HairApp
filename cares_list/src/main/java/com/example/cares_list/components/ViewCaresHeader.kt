package com.example.cares_list.components

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.cares_list.databinding.ViewCaresHeaderBinding
import com.example.corev2.ui.bindLayout

internal class ViewCaresHeader(context: Context, attrs: AttributeSet) :
    FrameLayout(context, attrs) {

    private val binding = bindLayout(ViewCaresHeaderBinding::inflate)
}