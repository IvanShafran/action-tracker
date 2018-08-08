package me.shafran.actiontracker.ui.view.actions

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_actions.*
import me.shafran.actiontracker.R
import me.shafran.actiontracker.data.entity.Action
import me.shafran.actiontracker.di.Scopes
import me.shafran.actiontracker.ui.presentation.actions.ActionsPresenter
import me.shafran.actiontracker.ui.presentation.actions.ActionsView
import me.shafran.actiontracker.ui.view.base.BaseFragment
import toothpick.Toothpick

class ActionsFragment : BaseFragment(), ActionsView, ActionsAdapter.Listener {

    private val actionsAdapter: ActionsAdapter by lazy { ActionsAdapter() }

    @InjectPresenter
    lateinit var actionsPresenter: ActionsPresenter

    @ProvidePresenter
    fun providePresenter(): ActionsPresenter {
        return Toothpick
                .openScope(Scopes.ROOT_SCOPE)
                .getInstance(ActionsPresenter::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_actions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actionsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = actionsAdapter
        }

        actionsAdapter.listener = this

        createActionButton.setOnClickListener { actionsPresenter.onCreateActionClick() }
    }

    override fun onCreateEventClick(action: Action) {
        actionsPresenter.onCreateEventClick(action)
    }

    override fun onOpenActionClick(action: Action) {
        actionsPresenter.onOpenActionClick(action)
    }

    override fun showActions(actions: List<Action>) {
        actionsAdapter.actions = actions
        actionsAdapter.notifyDataSetChanged()
    }
}
