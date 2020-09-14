package com.example.hairapp.page_care_form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.core.domain.Care
import javax.inject.Inject

class CareFormViewModel @Inject constructor() : ViewModel() {

    val careOptions: LiveData<Array<String>> = liveData {
        val options =  arrayOf(
            Care.OMO().methodName,
            Care.CG().methodName,
            Care.Custom().methodName
        )
        emit(options)
    }

    val date = MutableLiveData<String>()
    val careMethod = MutableLiveData<String>()

    init {
        val defaultCare = Care.OMO()
        date.value = defaultCare.date.toString()
        careMethod.value = defaultCare.methodName
    }
}