package me.shafran.actiontracker.ui.presentation.create_event

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import me.shafran.actiontracker.data.repository.datasource.ActionId
import javax.inject.Inject

@InjectViewState
class CreateEventPresenter @Inject constructor() : MvpPresenter<CreateEventView>() {

    lateinit var actionId: ActionId
}
