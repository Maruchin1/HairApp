package com.example.common.modals

import android.content.DialogInterface
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.example.common.base.BaseModal
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AppModal {

    suspend fun <T : ActionsModal.Action>openActions(
        manager: FragmentManager,
        actions: List<T>
    ): T? = suspendCoroutine { coroutine ->
        ActionsModal(
            actions = actions,
            onActionClicked = { coroutine.resume(it) }
        ).run {
            onClosedResumeCoroutineWithNull(coroutine)
            show(manager, "ActionModal")
        }
    }

    private fun <T, VB : ViewBinding> BaseModal<VB>.onClosedResumeCoroutineWithNull(coroutine: Continuation<T?>) {
        onCancel(object : DialogInterface {
            override fun cancel() {
                coroutine.resume(null)
            }

            override fun dismiss() {
                coroutine.resume(null)
            }
        })
    }
}