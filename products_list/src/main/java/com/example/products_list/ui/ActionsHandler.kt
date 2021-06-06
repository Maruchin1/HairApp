package com.example.products_list.ui

import com.example.navigation.ProductDetailsDestination
import com.example.navigation.ProductDetailsParams
import com.example.products_list.model.AddNewProductUseCase
import com.example.products_list.model.ProductsListViewModel
import com.example.shared_ui.BaseActionsHandler

internal class ActionsHandler(
    private val productDetailsDestination: ProductDetailsDestination
) : BaseActionsHandler(),
    ProductsListViewModel.Actions,
    AddNewProductUseCase.Actions {

    override suspend fun openProductDetails(productId: Long) {
        activity?.let {
            productDetailsDestination.navigate(
                originActivity = it,
                params = ProductDetailsParams(productId)
            )
        }
    }
}