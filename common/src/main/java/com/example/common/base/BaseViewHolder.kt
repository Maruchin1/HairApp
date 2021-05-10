package com.example.common.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BaseViewHolder<B : ViewBinding>(val binding: B) : RecyclerView.ViewHolder(binding.root)