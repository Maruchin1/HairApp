package com.example.hairapp.page_care

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.lifecycleScope
import com.example.hairapp.R
import com.example.hairapp.databinding.ActivityCareBinding
import com.example.hairapp.framework.*
import com.example.hairapp.page_home.CareListFragment
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_care.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CareActivity : AppCompatActivity() {

    private val viewModel: CareViewModel by viewModel()
    private val stepsFragment by lazy { CareStepsFragment() }
    private val photosFragment by lazy { CarePhotosFragment() }

    fun selectDate() = datePickerDialog {
        viewModel.setDate(it)
    }

    fun addProduct() {
        stepsFragment.addStep()
    }

    fun addPhoto() {
        ImagePicker.with(this)
            .crop(x = 4f, y = 3f)
            .compress(maxSize = 1024)
            .maxResultSize(width = 1080, height = 810)
            .start()
    }

    fun deleteCare() = confirmDialog(
        title = getString(R.string.confirm_delete),
        message = getString(R.string.care_activity_confirm_delete_message)
    ) {
        lifecycleScope.launch {
            viewModel.deleteCare().onFailure {
                showErrorSnackbar(it.message)
            }.onSuccess {
                finish()
            }
        }
    }

    fun saveCare() {
        lifecycleScope.launch {
            val steps = stepsFragment.getSteps()
            viewModel.saveCare(steps).onFailure {
                showErrorSnackbar(it.message)
            }.onSuccess {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind<ActivityCareBinding>(R.layout.activity_care, viewModel)
        setStatusBarColor(R.color.color_primary)
        setNavigationColor(R.color.color_white)
        setupTabs()

        input_date.inputType = 0
        input_care_type.inputType = 0

        setCareTypeListener()
        checkIfEdit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            data?.data?.let { viewModel.addPhoto(it.toString()) }
        }
    }

    private fun setCareTypeListener() {
        input_care_type.doOnTextChanged { text, _, _, _ ->
            Converter.inverseCareType(text?.toString())?.let {
                viewModel.setCareType(it)
            }
        }
    }

    private fun checkIfEdit() {
        val editCareId = intent.getIntExtra(IN_EDIT_CARE_ID, -1)
        if (editCareId != -1)
            setEditCare(editCareId)
    }

    private fun setEditCare(careId: Int) = lifecycleScope.launch {
        toolbar.title = "Pielęgnacja"
        viewModel.setEditCareAsync(careId)
            .await()
            .onFailure { showErrorSnackbar(it.message) }
    }

    private fun setupTabs() {
        tabs_pager.adapter = TabsAdapter()
        tabs.setupWithViewPager(tabs_pager)

        val stepsTab = tabs.getTabAt(CareTab.STEPS.position)
        stepsTab?.icon = ContextCompat.getDrawable(this, R.drawable.ic_round_list_24)

        val photosTab = tabs.getTabAt(CareTab.PHOTOS.position)
        photosTab?.icon = ContextCompat.getDrawable(this, R.drawable.ic_round_photo_library_24)
    }

    inner class TabsAdapter : FragmentStatePagerAdapter(
        supportFragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
        override fun getCount(): Int {
            return 2
        }

        override fun getItem(position: Int): Fragment {
            return when (CareTab.byPosition(position)) {
                CareTab.STEPS -> stepsFragment
                CareTab.PHOTOS -> photosFragment
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (CareTab.byPosition(position)) {
                CareTab.STEPS -> "Kroki"
                CareTab.PHOTOS -> "Zdjęcia"
            }
        }

    }

    private enum class CareTab(val position: Int) {
        STEPS(0),
        PHOTOS(1);

        companion object {
            fun byPosition(position: Int) = when (position) {
                0 -> STEPS
                1 -> PHOTOS
                else -> throw IllegalStateException("Tab number has to be 0 (steps) or 1 (photos)")
            }
        }
    }

    companion object {
        private const val IN_EDIT_CARE_ID = "in-edit-care-id"

        fun makeIntent(context: Context, editCareId: Int?): Intent {
            return Intent(context, CareActivity::class.java)
                .putExtra(IN_EDIT_CARE_ID, editCareId)
        }
    }
}