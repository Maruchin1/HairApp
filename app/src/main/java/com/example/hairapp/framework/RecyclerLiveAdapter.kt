package com.example.hairapp.framework

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hairapp.BR

class RecyclerLiveAdapter<T : Any> private constructor(
    private val controller: Any,
    private val lifecycleOwner: LifecycleOwner,
    private val layoutResId: Int,
    source: LiveData<List<T>>,
    compareItemsBy: ((item: T) -> Any)?
) : RecyclerView.Adapter<RecyclerViewHolder>() {

    private val itemsList = mutableListOf<T>()
    private val diffCallback = compareItemsBy?.let { CustomDiffCallback(it) }

    init {
        source.observe(lifecycleOwner) {
            updateItemsList(it)
        }
    }

    private fun updateItemsList(newList: List<T>?) {
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
                diffCallback.setLists(oldList = itemsList, newList = newList)
                val diffResult = DiffUtil.calculateDiff(diffCallback)
                itemsList.clear()
                itemsList.addAll(newList)
                diffResult.dispatchUpdatesTo(this)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutResId, parent, false)
        return RecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val item = itemsList[position]
        holder.bind(item = item, handler = controller)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    companion object {

        fun <T : Any> build(
            fragment: Fragment,
            layoutResId: Int,
            source: LiveData<List<T>>,
            compareItemsBy: ((item: T) -> Any)? = null
        ) = RecyclerLiveAdapter(
            controller = fragment,
            lifecycleOwner = fragment.viewLifecycleOwner,
            layoutResId = layoutResId,
            source = source,
            compareItemsBy = compareItemsBy
        )
    }
}

class RecyclerViewHolder(private val binding: ViewDataBinding) :
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