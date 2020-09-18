package com.example.hairapp.framework

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hairapp.BR
import java.util.*

open class RecyclerAdapter<T : Any>(
    private val controller: Any,
    private val layoutResId: Int
) : RecyclerView.Adapter<BindingViewHolder>() {

    private val itemsList = mutableListOf<T>()

    private var diffCallback: CustomDiffCallback<T>? = null
    private var setupItem: ((RecyclerView.ViewHolder, T) -> Unit)? = null

    fun withItemComparator(compareBy: (item: T) -> Any): RecyclerAdapter<T> {
        diffCallback = CustomDiffCallback(compareBy)
        return this
    }

    fun withItemSetup(setup: (RecyclerView.ViewHolder, T) -> Unit): RecyclerAdapter<T> {
        setupItem = setup
        return this
    }

    fun getItem(position: Int): T? {
        return if (itemsList.indices.contains(position)) itemsList[position] else null
    }

    fun getItemPosition(item: T): Int {
        return itemsList.indexOf(item)
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(itemsList, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(itemsList, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    fun addItem(item: T, position: Int = 0) {
        itemsList.add(position, item)
        notifyItemInserted(position)
    }

    fun removeItem(position: Int) {
        itemsList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateSingleItem(item: T, position: Int) {

    }

    fun updateItems(newList: List<T>?) {
        when {
            newList == null -> {
                itemsList.clear()
                notifyDataSetChanged()
            }
            diffCallback == null -> {
                itemsList.clear()
                itemsList.addAll(newList)
                notifyDataSetChanged()
            }
            else -> {
                diffCallback!!.setLists(oldList = itemsList, newList = newList)
                val diffResult = DiffUtil.calculateDiff(diffCallback!!)
                itemsList.clear()
                itemsList.addAll(newList)
                diffResult.dispatchUpdatesTo(this)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutResId, parent, false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val item = itemsList[position]
        holder.bind(item = item, handler = controller)
        setupItem?.invoke(holder, item)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    companion object {
        fun <T : Any> build(
            fragment: Fragment,
            layoutResId: Int
        ) = RecyclerAdapter<T>(
            controller = fragment,
            layoutResId = layoutResId
        )
    }
}

class RecyclerLiveAdapter<T : Any> constructor(
    private val controller: Any,
    private val lifecycleOwner: LifecycleOwner,
    private val layoutResId: Int,
    source: LiveData<List<T>>,
) : RecyclerAdapter<T>(controller, layoutResId) {

    init {
        source.observe(lifecycleOwner) {
            updateItems(it)
        }
    }

    companion object {
        fun <T : Any> build(
            fragment: Fragment,
            layoutResId: Int,
            source: LiveData<List<T>>
        ) = RecyclerLiveAdapter(
            controller = fragment,
            lifecycleOwner = fragment.viewLifecycleOwner,
            layoutResId = layoutResId,
            source = source
        )
    }
}

class BindingViewHolder(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val view = binding.root

    fun bind(item: Any, handler: Any) {
        binding.apply {
            setVariable(BR.item, item)
            setVariable(BR.controller, handler)
            binding.executePendingBindings()
        }
    }
}

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