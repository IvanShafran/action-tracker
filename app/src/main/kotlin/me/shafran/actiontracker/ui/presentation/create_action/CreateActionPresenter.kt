package me.shafran.actiontracker.ui.presentation.create_action

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

@InjectViewState
class CreateActionPresenter : MvpPresenter<CreateActionView>() {

    fun onCreateButtonClicked(actionName: String) {
    }

    fun onCancelButtonClicked() {
    }
}
