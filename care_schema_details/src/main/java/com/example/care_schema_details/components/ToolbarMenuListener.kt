package com.example.care_schema_details.components

import android.content.Context
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.example.care_schema_details.R
import com.example.common.modals.AppDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

internal class ToolbarMenuListener(
    private val context: Context,
    private val coroutineScope: CoroutineScope,
    private val viewModel: CareSchemaDetailsViewModel,
    private val appDialog: AppDialog,
) : Toolbar.OnMenuItemClickListener, KoinComponent {

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.option_change_schema_name -> changeSchemaName()
            R.id.option_delete_schema -> deleteSchema()
        }
        return true
    }

    private fun changeSchemaName() = coroutineScope.launch {
        appDialog.typeText(
            context = context,
            title = context.getString(R.string.change_care_schema_name)
        )?.let { newName ->
            viewModel.changeSchemaName(newName)
        }
    }

    private fun deleteSchema() = coroutineScope.launch {
        val confirmed = appDialog.confirm(
            context = context,
            title = context.getString(R.string.delete_care_schema)
        )
        if (confirmed) {
            viewModel.deleteSchema()
        }
    }
}