package com.example.hairapp.page_home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.core.domain.Product
import com.example.hairapp.R
import com.example.hairapp.common.ProductItemController
import com.example.hairapp.framework.RecyclerLiveAdapter
import com.example.hairapp.page_product.ProductActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_products_list.*

@AndroidEntryPoint
class ProductsListFragment : Fragment(), ProductItemController {

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_products_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.adapter = RecyclerLiveAdapter(
            controller = this,
            lifecycleOwner = viewLifecycleOwner,
            layoutResId = R.layout.item_product,
            source = viewModel.products
        ).withItemComparator { it.id }
    }

    override fun onProductSelected(product: Product) {
        val intent = ProductActivity.makeIntent(requireContext(), product.id)
        startActivity(intent)
    }
}