package me.shafran.actiontracker.navigation

import android.content.Context
import android.content.Intent
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import me.shafran.actiontracker.ui.view.create_action.CreateActionDialogFragment
import ru.terrakok.cicerone.android.SupportAppNavigator
import ru.terrakok.cicerone.commands.Forward

/**
 * Navigator which adding dialog navigation functionality.
 * Support only forward command for dialogs.
 */
class AppNavigator(
        private val activity: FragmentActivity,
        containerId: Int
) : SupportAppNavigator(activity, containerId) {

    override fun forward(command: Forward) {
        var dialogFragment = createDialogFragment(command.screenKey, command.transitionData)
        if (dialogFragment == null) {
            super.forward(command)
        } else {
            dialogFragment.show(activity.supportFragmentManager, null)
        }
    }

    private fun createDialogFragment(screen: String, data: Any?): DialogFragment? {
        return when (screen) {
            Screens.CREATE_ACTION -> CreateActionDialogFragment.newInstance()
            else -> null
        }
    }

    override fun createActivityIntent(context: Context, screenKey: String, data: Any?): Intent? {
        return null
    }

    override fun createFragment(screenKey: String, data: Any?): Fragment? {
        return when (screenKey) {
            Screens.CREATE_ACTION -> CreateActionDialogFragment.newInstance()
            else -> null
        }
    }
}
