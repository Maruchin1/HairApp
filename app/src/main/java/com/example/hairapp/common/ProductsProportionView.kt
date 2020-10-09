package com.example.hairapp.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import androidx.databinding.BindingAdapter
import com.example.core.domain.ProductsProportion
import com.example.hairapp.R
import kotlinx.android.synthetic.main.view_products_proportion.view.*

class ProductsProportionView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    var productsProportions: ProductsProportion? = null
        set(value) {
            field = value
            if (value != null) {
                view_products_proportion_humectants.setWeight(value.humectants)
                view_products_proportion_emollients.setWeight(value.emollients)
                view_products_proportion_proteins.setWeight(value.proteins)
            }
        }

    init {
        inflate(context, R.layout.view_products_proportion, this)
    }

    private fun View.setWeight(weight: Float) {
        updateLayoutParams<LinearLayout.LayoutParams> { this.weight = weight }
    }

    companion object {

        @BindingAdapter("app:productsProportion")
        @JvmStatic
        fun setProductsProportion(view: ProductsProportionView, value: ProductsProportion?) {
            view.productsProportions = value
        }
    }
}