package me.shafran.actiontracker.ui.view.action_detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_action_detail.*
import me.shafran.actiontracker.R
import me.shafran.actiontracker.data.entity.Action
import me.shafran.actiontracker.data.entity.Event
import me.shafran.actiontracker.data.repository.datasource.ActionId
import me.shafran.actiontracker.di.Scopes
import me.shafran.actiontracker.ui.presentation.action_detail.ActionDetailPresenter
import me.shafran.actiontracker.ui.presentation.action_detail.ActionDetailView
import me.shafran.actiontracker.ui.view.base.BaseFragment
import toothpick.Toothpick

class ActionDetailFragment : BaseFragment(), ActionDetailView, EventsAdapter.Listener {

    companion object {
        private const val ACTION_ID_KEY = "ACTION_ID_KEY"

        fun newInstance(actionId: Long): Fragment {
            val fragment = ActionDetailFragment()

            val arguments = Bundle()
            arguments.putLong(ACTION_ID_KEY, actionId)
            fragment.arguments = arguments

            return fragment
        }
    }

    @InjectPresenter
    lateinit var presenter: ActionDetailPresenter

    @ProvidePresenter
    fun providePresenter(): ActionDetailPresenter {
        val presenter = Toothpick
                .openScope(Scopes.ROOT_SCOPE)
                .getInstance(ActionDetailPresenter::class.java)

        val actionId = getNonNullArgumentsOrThrow().getLong(ACTION_ID_KEY)
        presenter.actionId = ActionId(actionId)

        return presenter
    }

    private val adapter: EventsAdapter by lazy { EventsAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_action_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            this@apply.adapter = this@ActionDetailFragment.adapter
        }

        adapter.listener = this

        createEventButton.setOnClickListener { presenter.onCreateEventClick() }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_detail_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuActionDelete -> {
                presenter.onDeleteActionClick()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showAction(action: Action) = adapter.showEvents(action.events)

    override fun showConfirmDeleteActionDialog() {
        val dialog = AlertDialog
                .Builder(getNonNullContextOrThrow())
                .setPositiveButton(android.R.string.yes) { _, _ -> presenter.onDeleteActionConfirmedClick() }
                .setNegativeButton(android.R.string.no, null)
                .setMessage(R.string.action_delete_confirm_dialog_message)
                .create()
        dialog.show()
    }

    override fun onDeleteEventClick(event: Event) = presenter.onDeleteEventClick(event)
}
