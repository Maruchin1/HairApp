package com.example.hairapp.page_care_form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.core.domain.Care
import com.example.core.domain.CareProduct
import javax.inject.Inject

class CareFormViewModel @Inject constructor() : ViewModel() {

    val careOptions: LiveData<Array<String>> = liveData {
        val options =  arrayOf("OMO", "CG", "Własne")
        emit(options)
    }

    val defaultCare = Care(
        before = mutableListOf(
            CareProduct.Extra(null),
            CareProduct.Extra(null),
            CareProduct.Extra(null)
        ),
        main = listOf(
            CareProduct.Main.Conditioner(null),
            CareProduct.Main.Shampoo(null)
        ),
        after = mutableListOf(
            CareProduct.Extra(null),
            CareProduct.Extra(null),
            CareProduct.Extra(null),
            CareProduct.Extra(null)
        )
    )

    val date = MutableLiveData<String>()
    val careMethod = MutableLiveData<String>()

    init {
        date.value = defaultCare.date.toString()
        careMethod.value = "OMO"
    }
}