package com.example.common.modals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.common.base.BaseModal
import com.example.common.base.BaseRecyclerAdapter
import com.example.common.databinding.ItemModalActionBinding
import com.example.common.databinding.ModalActionsBinding

class ActionsModal<T : ActionsModal.Action>(
    private val actions: List<T>,
    private val onActionClicked: (action: T) -> Unit
) : BaseModal<ModalActionsBinding>() {

    private val adapter by lazy { Adapter() }

    override fun bindModal(inflater: LayoutInflater, container: ViewGroup?): ModalActionsBinding {
        return ModalActionsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionsRecycler()
    }

    private fun setupActionsRecycler() {
        binding.actionsRecycler.adapter = adapter
        adapter.updateItems(actions)
    }

    interface Action {
        val iconResId: Int
        val nameResId: Int
    }

    inner class Adapter : BaseRecyclerAdapter<T, ItemModalActionBinding>() {

        override fun onBindItemView(
            layoutInflater: LayoutInflater,
            parent: ViewGroup
        ): ItemModalActionBinding {
            return ItemModalActionBinding.inflate(layoutInflater, parent, false)
        }

        override fun onBindItemData(binding: ItemModalActionBinding, item: T) {
            binding.apply {
                actionIcon.setImageResource(item.iconResId)
                actionName.text = requireContext().getString(item.nameResId)
                container.setOnClickListener {
                    onActionClicked(item)
                    dismiss()
                }
            }
        }
    }
}