package me.shafran.actiontracker.ui.presentation.action_detail

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import me.shafran.actiontracker.data.entity.Action

interface ActionDetailView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showAction(action: Action)
}
