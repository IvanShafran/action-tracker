package me.shafran.actiontracker.ui.presentation.actions

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.Disposable
import me.shafran.actiontracker.data.entity.Action
import me.shafran.actiontracker.data.repository.ActionRepository
import me.shafran.actiontracker.data.repository.datasource.InsertEventData
import me.shafran.actiontracker.navigation.Screens
import me.shafran.actiontracker.rx.RxSchedulers
import ru.terrakok.cicerone.Router
import java.util.Calendar
import javax.inject.Inject

@InjectViewState
class ActionsPresenter @Inject constructor(
        private val schedulers: RxSchedulers,
        private val router: Router,
        private val actionRepository: ActionRepository
) : MvpPresenter<ActionsView>() {

    private var disposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        disposable = actionRepository.getAllActionObservable()
                .observeOn(schedulers.ui())
                .subscribe { actions ->
                    viewState.showActions(actions)
                }
    }

    fun onCreateActionClick() {
        router.navigateTo(Screens.CREATE_ACTION)
    }

    fun onOpenActionClick(action: Action) = router.navigateTo(Screens.ACTION_DETAIL, action.id)

    fun onCreateEventClick(action: Action) {
        actionRepository
                .getInsertEventSingle(InsertEventData(action.id, 1, Calendar.getInstance()))
                .subscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}
