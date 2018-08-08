package me.shafran.actiontracker.ui.view.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.view.View
import me.shafran.actiontracker.ui.view.utils.DialogOnBackPressDismissListener

abstract class BaseBottomSheetDialogFragment : BaseDialogFragment() {

    protected abstract var layoutRes: Int

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = createView()
        return createDialog(view)
    }

    private fun createView(): View {
        val view = getNonNullActivityOrThrow()
                .layoutInflater
                .inflate(layoutRes, null)

        setupViews(view)

        return view
    }

    private fun createDialog(view: View): Dialog {
        isCancelable = false

        val bottomSheetDialog = BottomSheetDialog(
                getNonNullContextOrThrow(),
                theme
        )
        bottomSheetDialog.setContentView(view)

        bottomSheetDialog.setOnKeyListener(DialogOnBackPressDismissListener(this))

        return bottomSheetDialog
    }

    protected open fun setupViews(view: View) {
        // For overriding
    }
}
