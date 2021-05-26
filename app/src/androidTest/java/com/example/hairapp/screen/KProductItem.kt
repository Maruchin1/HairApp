package com.example.hairapp.screen

import android.view.View
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.text.KTextView
import com.example.hairapp.R
import org.hamcrest.Matcher

class KProductItem(parent: Matcher<View>) : KRecyclerItem<KProductItem>(parent) {

    val image = KImageView(parent) { withId(R.id.image) }

    val productType = KTextView(parent) { withId(R.id.product_type) }

    val productName = KTextView(parent) { withId(R.id.product_name) }

    val manufacturer = KTextView(parent) { withId(R.id.manufacturer) }
}