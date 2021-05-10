package com.example.common.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseRecyclerAdapter<T, B : ViewBinding> : RecyclerView.Adapter<BaseViewHolder<B>>() {

    protected val itemsList = mutableListOf<T>()

    private var diffCallback: BaseDiffCallback<T>? = null

    open fun addSource(source: LiveData<List<T>>, lifecycleOwner: LifecycleOwner) {
        source.observe(lifecycleOwner) { updateItems(it) }
    }

    open fun updateItems(newItemsList: List<T>?) {
        if (diffCallback == null) {
            itemsList.clear()
            newItemsList?.let { itemsList.addAll(it) }
            notifyDataSetChanged()
        } else {
            diffCallback!!.setLists(
                oldList = itemsList,
                newList = newItemsList ?: emptyList()
            )
            val diffResult = DiffUtil.calculateDiff(diffCallback!!)
            itemsList.clear()
            newItemsList?.let { itemsList.addAll(it) }
            diffResult.dispatchUpdatesTo(this)
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<B> {
        val inflater = LayoutInflater.from(parent.context)
        val binding: B = onBindItemView(inflater, parent)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<B>, position: Int) {
        onBindItemData(binding = holder.binding, item = itemsList[position])
    }

    protected abstract fun onBindItemView(layoutInflater: LayoutInflater, parent: ViewGroup): B

    protected abstract fun onBindItemData(binding: B, item: T)
}