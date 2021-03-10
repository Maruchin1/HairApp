package com.example.hairapp.page_care

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.lifecycleScope
import com.example.core.domain.CareSchema
import com.example.hairapp.R
import com.example.hairapp.databinding.ActivityCareBinding
import com.example.hairapp.framework.*
import kotlinx.android.synthetic.main.activity_care.*
import kotlinx.android.synthetic.main.activity_care.tabs
import kotlinx.android.synthetic.main.activity_care.tabs_pager
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CareActivity : AppCompatActivity() {

    private val viewModel: CareViewModel by viewModel()
    private val stepsFragment by lazy { CareStepsFragment() }
    private val photosFragment by lazy { CarePhotosFragment() }
    private val notesFragment by lazy { CareNotesFragment() }

    fun selectDate() = lifecycleScope.launch {
        Dialog.pickDate(supportFragmentManager)?.let { date ->
            viewModel.setDate(date)
        }
    }

    fun addStep() = lifecycleScope.launch {
        Dialog.selectCareStep(this@CareActivity)?.let { type ->
            stepsFragment.addStep(type)
        }
    }

    fun addPhoto() {
        Dialog.pickImage(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindActivity<ActivityCareBinding>(R.layout.activity_care, viewModel)
        SystemColors(this).darkStatusBar().lightNavigationBar().apply()
        setupTabs()
        checkInputParams()

        toolbar.onMenuOptionClick = { saveCare() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            data?.data?.let { viewModel.addPhoto(it.toString()) }
        }
    }

    private fun saveCare() = lifecycleScope.launch {
        val steps = stepsFragment.getSteps()
        viewModel.saveCare(steps)
            .onFailure { Snackbar.error(this@CareActivity, it) }
            .onSuccess { finish() }
    }

    private fun checkInputParams() = lifecycleScope.launch {
        val editCareId = intent.getIntExtra(IN_EDIT_CARE_ID, -1)
        val newCareSchemaId = intent.getIntExtra(IN_NEW_CARE_SCHEMA_ID, -1)
        when {
            editCareId != -1 -> viewModel.setEditCare(editCareId)
            newCareSchemaId != -1 -> viewModel.setNewCareSchema(newCareSchemaId)
            else -> null
        }?.onFailure { Snackbar.error(this@CareActivity, it) }
    }

    private fun setupTabs() {
        tabs_pager.adapter = TabsAdapter()
        tabs.setupWithViewPager(tabs_pager)

        val stepsTab = tabs.getTabAt(CareTab.STEPS.position)
        stepsTab?.icon = ContextCompat.getDrawable(this, R.drawable.ic_round_list_24)

        val photosTab = tabs.getTabAt(CareTab.PHOTOS.position)
        photosTab?.icon = ContextCompat.getDrawable(this, R.drawable.ic_round_photo_library_24)

        val notesTab = tabs.getTabAt(CareTab.NOTES.position)
        notesTab?.icon = ContextCompat.getDrawable(this, R.drawable.ic_round_notes_24)

        CareTabsFabsMediator(this)
    }

    inner class TabsAdapter : FragmentStatePagerAdapter(
        supportFragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
        override fun getCount(): Int {
            return 3
        }

        override fun getItem(position: Int): Fragment {
            return when (CareTab.byPosition(position)) {
                CareTab.STEPS -> stepsFragment
                CareTab.PHOTOS -> photosFragment
                CareTab.NOTES -> notesFragment
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return CareTab.byPosition(position).title
        }
    }

    internal enum class CareTab(val position: Int, val title: String) {
        STEPS(0, "Kroki"),
        PHOTOS(1, "Zdjęcia"),
        NOTES(2, "Notatki");

        companion object {
            fun byPosition(position: Int) = when (position) {
                0 -> STEPS
                1 -> PHOTOS
                2 -> NOTES
                else -> throw IllegalStateException("Tab number has to be 0 (steps), 1 (photos) or 2 (notes)")
            }
        }
    }

    companion object {
        private const val IN_EDIT_CARE_ID = "in-edit-care-id"
        private const val IN_NEW_CARE_SCHEMA_ID = "in-new-care-schema-id"

        fun makeIntent(context: Context, editCareId: Int): Intent {
            return Intent(context, CareActivity::class.java)
                .putExtra(IN_EDIT_CARE_ID, editCareId)
        }

        fun makeIntent(context: Context, newCareSchema: CareSchema): Intent {
            return Intent(context, CareActivity::class.java)
                .putExtra(IN_NEW_CARE_SCHEMA_ID, newCareSchema.id)
        }
    }
}