package com.example.shared_ui.extensions

import android.widget.EditText

fun EditText.setTextIfChanged(newText: String?) {
    if (text.toString() != newText) {
        setText(newText)
    }
}