package me.shafran.actiontracker.ui.presentation.create_action

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import me.shafran.actiontracker.data.entity.ActionType
import me.shafran.actiontracker.data.repository.ActionRepository
import me.shafran.actiontracker.data.repository.datasource.InsertActionData
import me.shafran.actiontracker.rx.RxSchedulers
import javax.inject.Inject

@InjectViewState
class CreateActionPresenter @Inject constructor(
        private val rxSchedulers: RxSchedulers,
        private val repository: ActionRepository
) : MvpPresenter<CreateActionView>() {

    private var isInProgress = false

    fun onCreateButtonClicked(actionName: String) {
        if (actionName.isNotEmpty() && !isInProgress) {
            isInProgress = true
            repository
                    .getInsertActionSingle(InsertActionData(actionName, ActionType.COUNTABLE))
                    .observeOn(rxSchedulers.ui())
                    .subscribe { _ ->
                        viewState.exit()
                    }
        }
    }
}
