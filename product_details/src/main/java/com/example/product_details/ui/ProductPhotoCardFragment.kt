package com.example.product_details.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.corev2.ui.BaseFragment
import com.example.corev2.ui.setPicassoUri
import com.example.corev2.ui.setVisibleOrGone
import com.example.product_details.databinding.FragmentProductPhotoCardBinding
import com.example.product_details.model.ProductDetailsViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

internal class ProductPhotoCardFragment : BaseFragment<FragmentProductPhotoCardBinding>(
    bindingInflater = FragmentProductPhotoCardBinding::inflate
) {

    private val viewModel by sharedViewModel<ProductDetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
    }

    private fun observeState() = lifecycleScope.launchWhenStarted {
        viewModel.state.collectLatest {
            Log.d("MyDebug", "ProductPhoto state changed")
            updatePhoto(it.product?.photoData)
            updateVisibility(it.product?.photoData)
        }
    }

    private fun updatePhoto(photoData: String?) {
        binding.image.setPicassoUri(photoData)
    }

    private fun updateVisibility(photoData: String?) {
        binding.root.setVisibleOrGone(photoData != null)
    }
}