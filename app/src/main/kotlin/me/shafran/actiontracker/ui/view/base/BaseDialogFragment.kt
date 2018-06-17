package me.shafran.actiontracker.ui.view.base

import android.content.Context
import com.arellomobile.mvp.MvpAppCompatDialogFragment

abstract class BaseDialogFragment : MvpAppCompatDialogFragment() {

    fun getNonNullContextOrThrow(): Context {
        return context ?: throw IllegalStateException("Context is null")
    }
}
