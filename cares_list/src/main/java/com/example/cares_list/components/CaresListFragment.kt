package com.example.cares_list.components

import android.os.Bundle
import android.view.View
import com.example.cares_list.databinding.FragmentCaresListBinding
import com.example.corev2.ui.BaseFragment

class CaresListFragment : BaseFragment<FragmentCaresListBinding>(
    bindingInflater = FragmentCaresListBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}