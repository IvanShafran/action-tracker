package me.shafran.actiontracker.ui.view.base

import com.arellomobile.mvp.MvpAppCompatFragment

abstract class BaseFragment : MvpAppCompatFragment() {

    /**
     * Handle back press.
     * Returns True if back press was handled and should not be handled in activity.
     */
    open fun onBackPressed(): Boolean = false
}
