package me.shafran.actiontracker.ui.view.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatDialogFragment

abstract class BaseDialogFragment : MvpAppCompatDialogFragment() {

    fun getNonNullContextOrThrow(): Context {
        return context ?: throw IllegalStateException("Context is null")
    }

    fun getNonNullActivityOrThrow(): Activity {
        return activity ?: throw IllegalStateException("Activity is null")
    }

    fun getNonNullArgumentsOrThrow(): Bundle {
        return arguments ?: throw IllegalStateException("Arguments is null")
    }
}
