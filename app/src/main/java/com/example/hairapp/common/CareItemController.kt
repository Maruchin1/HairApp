package com.example.hairapp.common

import com.example.core.domain.Care

interface CareItemController {
    fun onCareSelected(care: Care)
}