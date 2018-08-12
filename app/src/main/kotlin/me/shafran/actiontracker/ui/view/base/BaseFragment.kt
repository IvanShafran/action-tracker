package me.shafran.actiontracker.ui.view.base

import android.content.Context
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatFragment

abstract class BaseFragment : MvpAppCompatFragment() {

    fun getNonNullContextOrThrow(): Context {
        return context ?: throw IllegalStateException("Arguments is null")
    }

    fun getNonNullArgumentsOrThrow(): Bundle {
        return arguments ?: throw IllegalStateException("Arguments is null")
    }
}
