package me.shafran.actiontracker.ui.view.create_event

import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import me.shafran.actiontracker.R
import me.shafran.actiontracker.data.repository.datasource.ActionId
import me.shafran.actiontracker.di.Scopes
import me.shafran.actiontracker.ui.presentation.create_event.CreateEventPresenter
import me.shafran.actiontracker.ui.presentation.create_event.CreateEventView
import me.shafran.actiontracker.ui.view.base.BaseBottomSheetDialogFragment
import toothpick.Toothpick

class CreateEventDialogFragment : BaseBottomSheetDialogFragment(), CreateEventView {

    companion object {
        private const val ACTION_ID_KEY = "ACTION_ID_KEY"

        fun newInstance(actionId: Long): DialogFragment {
            val dialogFragment = CreateEventDialogFragment()

            val arguments = Bundle()
            arguments.putLong(ACTION_ID_KEY, actionId)
            dialogFragment.arguments = arguments

            return dialogFragment
        }
    }

    override var layoutRes: Int = R.layout.fragment_create_event

    @InjectPresenter
    lateinit var createEventPresenter: CreateEventPresenter

    @ProvidePresenter
    fun providePresenter(): CreateEventPresenter {
        val presenter = Toothpick
                .openScope(Scopes.ROOT_SCOPE)
                .getInstance(CreateEventPresenter::class.java)

        val actionId = getNonNullArgumentsOrThrow().getLong(ACTION_ID_KEY)
        presenter.actionId = ActionId(actionId)

        return presenter
    }
}
