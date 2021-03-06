package com.example.hairapp.page_peh_balance

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.hairapp.R
import com.example.hairapp.databinding.ActivityPehBalanceBinding
import com.example.hairapp.framework.bind
import com.example.hairapp.framework.setSystemColors
import org.koin.androidx.viewmodel.ext.android.viewModel

class PehBalanceActivity : AppCompatActivity() {

    private val viewModel: PehBalanceViewModel by viewModel()
    private lateinit var binding: ActivityPehBalanceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bind(R.layout.activity_peh_balance, viewModel)
        setSystemColors(R.color.color_primary)
    }

    companion object {
        fun makeIntent(context: Context): Intent {
            return Intent(context, PehBalanceActivity::class.java)
        }
    }
}