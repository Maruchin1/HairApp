package com.example.care_details.components

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import arrow.core.computations.either
import com.example.care_details.R
import com.example.care_details.databinding.ActivityCareDetailsBinding
import com.example.corev2.entities.PehBalance
import com.example.corev2.navigation.CareDetailsDestination
import com.example.corev2.navigation.Destination
import com.example.corev2.service.formatDayAndMonth
import com.example.corev2.service.formatDayOfWeekAndMonth
import com.example.corev2.ui.BaseActivity
import com.example.corev2.ui.SystemColors
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CareDetailsActivity : BaseActivity<ActivityCareDetailsBinding>(
    bindingInflater = ActivityCareDetailsBinding::inflate
) {

    private val params: CareDetailsDestination.Params?
        get() = intent.getParcelableExtra(Destination.EXTRA_PARAMS)

    private val viewModel: CareDetailsViewModel by viewModel()
    private val useCaseActions: UseCaseActions by inject()
    private val pagesAdapter by lazy { PagesAdapter() }
    private val stepsFragment = CareStepsFragment()
    private val photosFragment = PhotosFragment()
    private val notesFragment = CareNotesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        useCaseActions.bindToActivity(this)
        setupViewModel()
        setupPehBalanceBar()
        setupToolbar()
        setupPages()
    }

    override fun setupSystemColors(systemColors: SystemColors) {
        systemColors.apply {
            allLight()
        }
    }

    private fun setupViewModel() {
        params?.careId?.let {
            viewModel.onCareSelected(it)
        }
    }

    private fun setupToolbar() {
        viewModel.careDate.observe(this) {
            binding.toolbar.title = it.formatDayOfWeekAndMonth()
        }
        viewModel.schemaName.observe(this) {
            binding.toolbar.subtitle = it
        }
        binding.toolbar.apply {
            setNavigationOnClickListener { finish() }
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.select_date -> onChangeDateClicked()
                    R.id.delete_care -> onDeleteCareClicked()
                }
                true
            }
        }
    }

    private fun setupPehBalanceBar() {
        viewModel.steps.observe(this) { steps ->
            val products = steps.mapNotNull { it.product }
            binding.pehBalanceBar.pehBalance = PehBalance.fromProducts(products)
        }
    }

    private fun setupPages() {
        binding.pager.adapter = pagesAdapter
        binding.pagesTabs.apply {
            setupWithViewPager(binding.pager)
            getTabAt(0)?.setIcon(R.drawable.ic_round_format_list_numbered_24)
            getTabAt(1)?.setIcon(R.drawable.ic_round_photo_library_24)
            getTabAt(2)?.setIcon(R.drawable.ic_round_notes_24)
        }
        PagerFabMediator(binding.fab, binding.pager)
    }

    private fun onChangeDateClicked() = lifecycleScope.launch {
        viewModel.onChangeDateClicked()
    }

    private fun onDeleteCareClicked() = lifecycleScope.launch {
        viewModel.onDeleteCareClicked()
            .map { finish() }
    }

    private inner class PagesAdapter : FragmentStatePagerAdapter(supportFragmentManager) {

        override fun getCount(): Int {
            return 3
        }

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> stepsFragment
                1 -> photosFragment
                2 -> notesFragment
                else -> throw IllegalStateException("Invalid page position: $position")
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> getString(R.string.steps)
                1 -> getString(R.string.photos)
                2 -> getString(R.string.notes)
                else -> throw IllegalStateException("Invalid page position: $position")
            }
        }
    }
}