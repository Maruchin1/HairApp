package com.example.hairapp.framework

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import java.util.*

class RecyclerMutableLiveAdapter<T : Any>(
    private val controller: Any,
    private val lifecycleOwner: LifecycleOwner,
    private val layoutResId: Int,
    private val mutableSource: MutableLiveData<List<T>>,
    compareItemsBy: ((item: T) -> Any)?
) : RecyclerLiveAdapter<T>(controller, lifecycleOwner, layoutResId, mutableSource, compareItemsBy),
    ItemTouchHelperAdapter {

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
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
        return true
    }

    override fun onItemDismiss(position: Int) {
        itemsList.removeAt(position)
        notifyItemRemoved(position)
    }
}