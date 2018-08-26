package me.shafran.actiontracker.ui.view.base

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatFragment

abstract class BaseFragment : MvpAppCompatFragment() {

    fun requireArguments(): Bundle {
        return arguments ?: throw IllegalStateException("Arguments is null")
    }
}
