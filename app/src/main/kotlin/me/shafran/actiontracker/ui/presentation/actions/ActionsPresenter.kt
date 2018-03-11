package me.shafran.actiontracker.ui.presentation.actions

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import me.shafran.actiontracker.data.repository.ActionRepository
import me.shafran.actiontracker.rx.RxSchedulers

@InjectViewState
class ActionsPresenter(
        private val schedulers: RxSchedulers,
        private val actionRepository: ActionRepository
) : MvpPresenter<ActionsView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        actionRepository.getAllActionObservable()
                .observeOn(schedulers.ui())
                .subscribe { actions ->
                    viewState.showActions(actions)
                }
    }

    fun onCreateActionClick() {

    }

}
