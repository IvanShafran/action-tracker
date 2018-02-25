package me.shafran.actiontracker.ui.view.actions

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import me.shafran.actiontracker.ui.presentation.actions.ActionsPresenter
import me.shafran.actiontracker.ui.presentation.actions.ActionsView
import me.shafran.actiontracker.ui.view.base.BaseFragment

class ActionsFragment : BaseFragment(), ActionsView {

    @InjectPresenter
    lateinit var actionsPresenter: ActionsPresenter


    @ProvidePresenter
    fun providePresenter(): ActionsPresenter {
        return ActionsPresenter()
    }

}
