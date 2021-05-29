package com.example.cares_list.components

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.cares_list.R
import com.example.cares_list.databinding.ViewCaresHeaderBinding
import com.example.corev2.service.formatDayOfWeekAndMonth
import com.example.corev2.ui.bindLayout
import java.time.LocalDate

internal class ViewCaresHeader(context: Context, attrs: AttributeSet) :
    FrameLayout(context, attrs) {

    private val binding = bindLayout(ViewCaresHeaderBinding::inflate)

    var today: LocalDate? = null
        set(value) {
            field = value
            binding.today.text = value?.formatDayOfWeekAndMonth()
        }

    var daysFromLastCare: Long
        get() = binding.counterDaysFromLastCare.value.toLong()
        set(value) {
            binding.counterDaysFromLastCare.value = value.toInt()
        }

    var onPehClick: (() -> Unit)? = null

    var onGalleryClick: (() -> Unit)? = null

    init {
        binding.apply {
            counterDaysFromLastCare.label = context.getString(R.string.days_ago)
            btnGallery.setOnClickListener { onPehClick?.invoke() }
            btnOpenStatistics.setOnClickListener { onGalleryClick?.invoke() }
        }
    }
}