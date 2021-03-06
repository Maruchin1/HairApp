package com.example.hairapp.page_photos_gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.hairapp.R
import com.example.hairapp.framework.BindingRecyclerAdapter
import com.example.hairapp.framework.BindingViewHolder

class GalleryAdapter(
    controller: Any
) : BindingRecyclerAdapter<GalleryItem>(controller, -1) {

    fun getItemType(value: Int): ItemType = when (value) {
        ItemType.HEADER.value -> ItemType.HEADER
        ItemType.PHOTO.value -> ItemType.PHOTO
        else -> throw IllegalStateException("Not matching value")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val layoutResId = getItemType(viewType).layoutResId
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, layoutResId, parent, false)
        return BindingViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemsList[position]) {
            is GalleryItem.DayHeader -> ItemType.HEADER.value
            is GalleryItem.Photo -> ItemType.PHOTO.value
        }
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val item = itemsList[position]
        when (item) {
            is GalleryItem.DayHeader -> holder.bind(item.date, controller)
            is GalleryItem.Photo -> holder.bind(item.data, controller)
        }
    }

    enum class ItemType(val value: Int, val layoutResId: Int) {
        HEADER(1, R.layout.item_day_header),
        PHOTO(2, R.layout.item_care_photo)
    }
}