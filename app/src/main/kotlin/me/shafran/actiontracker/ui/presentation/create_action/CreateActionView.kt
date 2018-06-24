package me.shafran.actiontracker.ui.presentation.create_action

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface CreateActionView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun exit()
}
