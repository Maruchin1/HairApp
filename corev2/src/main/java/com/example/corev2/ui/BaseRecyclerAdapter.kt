package com.example.corev2.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseRecyclerAdapter<T, VB : ViewBinding> :
    RecyclerView.Adapter<BaseViewHolder<VB>>() {

    protected val itemsList = mutableListOf<T>()

    protected abstract val inflateBinding: InflateBinding<VB>

    private var diffCallback: BaseDiffCallback<T>? = null

    open fun addSource(source: LiveData<List<T>>, lifecycleOwner: LifecycleOwner) {
        source.observe(lifecycleOwner) { updateItems(it) }
    }

    fun setItemComparator(itemsComparator: ItemsComparator<T>) {
        diffCallback = BaseDiffCallback(itemsComparator)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val inflater = LayoutInflater.from(parent.context)
        val binding: VB = inflateBinding(inflater, parent, false)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        onBindItemData(binding = holder.binding, item = itemsList[position])
    }

    protected abstract fun onBindItemData(binding: VB, item: T)
}