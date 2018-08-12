package me.shafran.actiontracker.navigation

import android.content.Context
import android.content.Intent
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import me.shafran.actiontracker.ui.view.action_detail.ActionDetailFragment
import me.shafran.actiontracker.ui.view.actions.ActionsFragment
import me.shafran.actiontracker.ui.view.create_action.CreateActionDialogFragment
import me.shafran.actiontracker.ui.view.create_event.CreateEventDialogFragment
import ru.terrakok.cicerone.android.SupportAppNavigator
import ru.terrakok.cicerone.commands.Forward

/**
 * Navigator which adding dialog navigation functionality.
 * Support only forward command for dialogs.
 */
class AppNavigator(
        activity: FragmentActivity,
        containerId: Int
) : SupportAppNavigator(activity, containerId) {

    private val fragmentManager: FragmentManager = activity.supportFragmentManager

    override fun forward(command: Forward) {
        val dialogFragment = createDialogFragment(command.screenKey, command.transitionData)
        if (dialogFragment == null) {
            super.forward(command)
        } else {
            dialogFragment.show(fragmentManager, null)
        }
    }

    private fun createDialogFragment(screenKey: String, data: Any?): DialogFragment? {
        return when (screenKey) {
            Screens.CREATE_ACTION -> CreateActionDialogFragment.newInstance()
            Screens.CREATE_EVENT -> {
                val actionId = data as Long
                CreateEventDialogFragment.newInstance(actionId)
            }
            else -> null
        }
    }

    override fun createActivityIntent(context: Context, screenKey: String, data: Any?): Intent? {
        return null
    }

    override fun createFragment(screenKey: String, data: Any?): Fragment? {
        return when (screenKey) {
            Screens.ACTIONS -> ActionsFragment.newInstance()
            Screens.ACTION_DETAIL -> {
                val actionId = data as Long
                ActionDetailFragment.newInstance(actionId)
            }
            else -> null
        }
    }
}
