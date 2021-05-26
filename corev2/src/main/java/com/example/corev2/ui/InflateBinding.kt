package com.example.corev2.ui

import android.view.LayoutInflater
import android.view.ViewGroup

typealias InflateActivityBinding<VB> = (inflater: LayoutInflater) -> VB

typealias InflateBinding<VB> = (
    inflater: LayoutInflater,
    parent: ViewGroup?,
    attachToParent: Boolean
) -> VB