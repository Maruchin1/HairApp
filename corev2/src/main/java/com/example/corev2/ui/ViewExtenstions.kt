package com.example.corev2.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewbinding.ViewBinding
import com.example.corev2.entities.Product
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso

fun View.setVisibleOrGone(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.setVisibleOrGoneSource(source: LiveData<Boolean>, lifecycleOwner: LifecycleOwner) {
    source.observe(lifecycleOwner) {
        visibility = if (it) View.VISIBLE else View.GONE
    }
}

fun ImageView.setPicassoUri(uriString: String?) {
    val uri = uriString?.let { Uri.parse(it) }
    Picasso.get().load(uri).into(this)
}

fun ImageView.setPicassoUriSource(source: LiveData<Uri?>, lifecycleOwner: LifecycleOwner) {
    source.observe(lifecycleOwner) {
        Picasso.get().load(it).into(this)
    }
}

fun TextInputEditText.setPicassoUriSource(
    source: MutableLiveData<String>,
    lifecycleOwner: LifecycleOwner
) {
    source.observe(lifecycleOwner) {
        if (text.toString() != it) {
            setText(it)
        }
    }
    doAfterTextChanged {
        source.value = it?.toString()
    }
}

fun Chip.setIsCheckedSource(source: MutableLiveData<Boolean>, lifecycleOwner: LifecycleOwner) {
    source.observe(lifecycleOwner) {
        isChecked = it
    }
    setOnCheckedChangeListener { _, isChecked ->
        source.value = isChecked
    }
}

fun <VB : ViewBinding> ViewGroup.bindLayout(bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB): VB {
    return bindingInflater(LayoutInflater.from(context), this, true)
}

fun View.setWeight(weight: Double) {
    updateLayoutParams<LinearLayout.LayoutParams> { this.weight = weight.toFloat() }
}

fun MaterialToolbar.setTitleSource(source: LiveData<String>, lifecycleOwner: LifecycleOwner) {
    source.observe(lifecycleOwner) {
        title = it
    }
}

fun TextView.setSource(source: LiveData<String>, lifecycleOwner: LifecycleOwner) {
    source.observe(lifecycleOwner) {
        text = it
    }
}