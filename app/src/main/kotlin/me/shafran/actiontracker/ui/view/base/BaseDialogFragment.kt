package me.shafran.actiontracker.ui.view.base

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatDialogFragment

abstract class BaseDialogFragment : MvpAppCompatDialogFragment() {

    fun requireArguments(): Bundle {
        return arguments ?: throw IllegalStateException("Arguments is null")
    }
}
