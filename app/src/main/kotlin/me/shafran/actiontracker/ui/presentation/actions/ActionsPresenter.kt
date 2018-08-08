package me.shafran.actiontracker.ui.presentation.actions

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import me.shafran.actiontracker.data.entity.Action
import me.shafran.actiontracker.data.repository.ActionRepository
import me.shafran.actiontracker.navigation.Screens
import me.shafran.actiontracker.rx.RxSchedulers
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ActionsPresenter @Inject constructor(
        private val schedulers: RxSchedulers,
        private val router: Router,
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
        router.navigateTo(Screens.CREATE_ACTION)
    }

    fun onOpenActionClick(action: Action) {
    }

    fun onCreateEventClick(action: Action) {
        router.navigateTo(Screens.CREATE_EVENT, action.id)
    }
}
