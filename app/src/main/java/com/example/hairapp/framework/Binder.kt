package com.example.hairapp.framework

import android.net.Uri
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import coil.load
import com.example.core.domain.Care
import com.example.hairapp.R
import com.google.android.material.appbar.MaterialToolbar
import java.time.LocalDate

object Binder {

    // Photo

    @BindingAdapter("app:srcUri", "app:defaultResource", requireAll = false)
    @JvmStatic
    fun setSourceUri(view: ImageView, uri: Uri?, defaultResourceId: Int?) {
        when {
            uri != null -> view.load(uri)
            defaultResourceId != null -> view.load(defaultResourceId)
            else -> view.load(R.drawable.ic_round_photo_24)
        }
    }

    @BindingAdapter("app:srcUri", "app:defaultResource", requireAll = false)
    @JvmStatic
    fun setSourceUri(view: ImageView, data: String?, defaultResourceId: Int?) {
        val uri = data?.let { Uri.parse(it) }
        setSourceUri(view, uri, defaultResourceId)
    }

    // Toolbar

    @BindingAdapter("app:onNavigationClick")
    @JvmStatic
    fun setOnNavigationClick(view: MaterialToolbar, action: (() -> Unit)?) {
        view.setNavigationOnClickListener { action?.invoke() }
    }

    // Visibility

    @BindingAdapter("app:visibleOrGone")
    @JvmStatic
    fun setVisibleOrGone(view: View, visible: Boolean?) {
        view.visibility = if (visible == true) View.VISIBLE else View.GONE
    }

    // EditText

    @BindingAdapter("app:careType")
    @JvmStatic
    fun setCareType(view: EditText, type: Care.Type?) {
        val currTypeText = view.text.toString()
        val newTypeText = Converter.careType(type)
        if (currTypeText != newTypeText) {
            view.setText(newTypeText)
        }
    }

    @BindingAdapter("app:careTypeOptions")
    @JvmStatic
    fun setCareTypeOptions(view: AutoCompleteTextView, options: Array<Care.Type>) {
        val stringOptions = options.map { Converter.careType(it) }
        val adapter = ArrayAdapter(view.context, R.layout.item_menu, stringOptions)
        view.setAdapter(adapter)
    }

    @BindingAdapter("app:date")
    @JvmStatic
    fun setDate(view: EditText, date: LocalDate?) {
        view.setText(Converter.date(date))
    }

    @InverseBindingAdapter(attribute = "app:date")
    @JvmStatic
    fun getDate(view: EditText): LocalDate? {
        return Converter.inverseDate(view.text.toString())
    }
}
