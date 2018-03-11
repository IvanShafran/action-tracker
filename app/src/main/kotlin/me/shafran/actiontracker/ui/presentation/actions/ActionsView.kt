package me.shafran.actiontracker.ui.presentation.actions

import com.arellomobile.mvp.MvpView
import me.shafran.actiontracker.data.entity.Action

interface ActionsView : MvpView {

    fun showActions(actions: List<Action>)
}
