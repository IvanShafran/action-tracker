package me.shafran.actiontracker.ui.presentation.action_detail

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import me.shafran.actiontracker.data.entity.Event

interface ActionDetailView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showEventsOnCalendar(events: List<Event>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showDayEvents(events: List<Event>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showConfirmDeleteActionDialog()
}
