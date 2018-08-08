package me.shafran.actiontracker.ui.view.create_action

import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.EditText
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import me.shafran.actiontracker.R
import me.shafran.actiontracker.di.Scopes
import me.shafran.actiontracker.ui.presentation.create_action.CreateActionPresenter
import me.shafran.actiontracker.ui.presentation.create_action.CreateActionView
import me.shafran.actiontracker.ui.view.base.BaseBottomSheetDialogFragment
import toothpick.Toothpick

class CreateActionDialogFragment : BaseBottomSheetDialogFragment(), CreateActionView {

    companion object {
        fun newInstance(): DialogFragment {
            return CreateActionDialogFragment()
        }
    }

    override var layoutRes: Int = R.layout.fragment_create_action

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

    override fun setupViews(view: View) {
        createButton = view.findViewById(R.id.create_button)
        createButton.setOnClickListener { onCreateButtonClick() }

        editView = view.findViewById(R.id.create_action_edit_text)
    }

    private fun onCreateButtonClick() {
        val actionName = editView.text.toString()
        createActionPresenter.onCreateButtonClicked(actionName)
    }

    override fun exit() {
        dismiss()
    }
}
