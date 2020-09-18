package com.example.hairapp.page_care_form

import androidx.lifecycle.*
import com.example.core.domain.Care
import com.example.core.domain.CareProduct
import com.example.hairapp.R
import com.example.hairapp.framework.updateState
import java.time.LocalDate
import javax.inject.Inject

class CareFormViewModel @Inject constructor() : ViewModel() {
    companion object {
        private const val OMO = "OMO"
        private const val CG = "CG"
        private const val CUSTOM = "Własne"
    }

    val careOptions: LiveData<Array<String>> = liveData {
        emit(arrayOf(OMO, CG, CUSTOM))
    }

    val date = MutableLiveData<String>()

    val careMethod = MutableLiveData<String>()

    val steps: LiveData<List<CareProduct>> = careMethod.map {
        val care = when (it) {
            OMO -> Care.OMO()
            CG -> Care.CG()
            CUSTOM -> Care.Custom()
            else -> throw IllegalStateException("Not matching careMethod: $careMethod")
        }
        care.steps
    }

    init {
        date.value = LocalDate.now().toString()
        careMethod.value = OMO
    }
}