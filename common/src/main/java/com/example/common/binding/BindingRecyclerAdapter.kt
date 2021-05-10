package com.example.common.binding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BindingRecyclerAdapter<T, V : ViewDataBinding>(
    private val layoutResId: Int
): RecyclerView.Adapter<BindingViewHolder<V>>() {

    protected val itemsList = mutableListOf<T>()

    private var diffCallback: CustomDiffCallback<T>? = null

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<V> {
        val inflater = LayoutInflater.from(parent.context)
        val binding: V = DataBindingUtil.inflate(inflater, layoutResId, parent, false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<V>, position: Int) {
        val item = itemsList[position]
        setupItemBinding(holder.binding, item)
        holder.binding.executePendingBindings()
    }

    protected abstract fun setupItemBinding(binding: V, item: T)
}

class BindingViewHolder<V : ViewDataBinding>(val binding: V) : RecyclerView.ViewHolder(binding.root)

private class CustomDiffCallback<T>(
    private val compareBy: (item: T) -> Any
) : DiffUtil.Callback() {

    private var oldList: List<T> = emptyList()
    private var newList: List<T> = emptyList()

    fun setLists(oldList: List<T>, newList: List<T>) {
        this.oldList = oldList
        this.newList = newList
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return compareBy(oldItem) == compareBy(newItem)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
