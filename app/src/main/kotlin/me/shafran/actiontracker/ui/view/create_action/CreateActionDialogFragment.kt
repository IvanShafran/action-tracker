package me.shafran.actiontracker.ui.view.create_action

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_create_action.*
import me.shafran.actiontracker.R
import me.shafran.actiontracker.ui.presentation.create_action.CreateActionPresenter
import me.shafran.actiontracker.ui.presentation.create_action.CreateActionView
import me.shafran.actiontracker.ui.view.base.BaseDialogFragment

class CreateActionDialogFragment : BaseDialogFragment(), CreateActionView {

    @InjectPresenter
    lateinit var createActionPresenter: CreateActionPresenter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialogBuilder = AlertDialog
                .Builder(getNonNullContextOrThrow())
                .setTitle(R.string.create_action_title)
                .setView(R.layout.fragment_create_action)
                .setNegativeButton(
                        R.string.create_action_negative_button,
                        { _, _ -> onCancelButtonClick() }
                )
                .setPositiveButton(
                        R.string.create_action_positive_button,
                        { _, _ -> onCreateButtonClick() }
                )

        isCancelable = false
        return alertDialogBuilder.create()
    }

    private fun onCancelButtonClick() {
        createActionPresenter.onCancelButtonClicked()
    }

    private fun onCreateButtonClick() {
        val actionName = createActionNameEditText.text.toString()
        createActionPresenter.onCreateButtonClicked(actionName)
    }
}
