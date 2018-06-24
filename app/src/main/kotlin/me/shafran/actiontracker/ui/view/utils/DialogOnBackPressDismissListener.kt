package me.shafran.actiontracker.ui.view.utils

import android.content.DialogInterface
import android.support.v4.app.DialogFragment
import android.view.KeyEvent

class DialogOnBackPressDismissListener(private val dialogFragment: DialogFragment) : DialogInterface.OnKeyListener {

    override fun onKey(dialog: DialogInterface, keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
            dialogFragment.dismiss()
            true
        } else {
            false
        }
    }
}
