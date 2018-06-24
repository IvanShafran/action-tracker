package me.shafran.actiontracker.ui.view.create_action

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.EditText
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import me.shafran.actiontracker.R
import me.shafran.actiontracker.di.Scopes
import me.shafran.actiontracker.ui.presentation.create_action.CreateActionPresenter
import me.shafran.actiontracker.ui.presentation.create_action.CreateActionView
import me.shafran.actiontracker.ui.view.base.BaseDialogFragment
import me.shafran.actiontracker.ui.view.utils.DialogOnBackPressDismissListener
import toothpick.Toothpick

class CreateActionDialogFragment : BaseDialogFragment(), CreateActionView {

    companion object {
        fun newInstance(): DialogFragment {
            return CreateActionDialogFragment()
        }
    }

    @InjectPresenter
    lateinit var createActionPresenter: CreateActionPresenter

    private lateinit var createButton: View
    private lateinit var editView: EditText

    @ProvidePresenter
    fun providePresenter(): CreateActionPresenter {
        return Toothpick
                .openScope(Scopes.ROOT_SCOPE)
                .getInstance(CreateActionPresenter::class.java)
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = getNonNullActivitytOrThrow()
                .layoutInflater
                .inflate(R.layout.fragment_create_action, null)

        val bottomSheetDialog = BottomSheetDialog(
                getNonNullContextOrThrow(),
                theme
        )
        bottomSheetDialog.setContentView(view)

        isCancelable = false
        bottomSheetDialog.setOnKeyListener(DialogOnBackPressDismissListener(this))

        createButton = view.rootView.findViewById(R.id.create_button)
        createButton.setOnClickListener { onCreateButtonClick() }

        editView = view.rootView.findViewById(R.id.create_action_edit_text)

        return bottomSheetDialog
    }

    private fun onCreateButtonClick() {
        val actionName = editView.text.toString()
        createActionPresenter.onCreateButtonClicked(actionName)
    }

    override fun exit() {
        dismiss()
    }
}
