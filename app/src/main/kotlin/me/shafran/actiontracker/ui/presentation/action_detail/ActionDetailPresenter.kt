package me.shafran.actiontracker.ui.presentation.action_detail

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.Disposable
import me.shafran.actiontracker.data.entity.Event
import me.shafran.actiontracker.data.repository.ActionRepository
import me.shafran.actiontracker.data.repository.datasource.ActionId
import me.shafran.actiontracker.data.repository.datasource.InsertEventData
import me.shafran.actiontracker.rx.RxSchedulers
import ru.terrakok.cicerone.Router
import java.util.Calendar
import javax.inject.Inject

@InjectViewState
class ActionDetailPresenter @Inject constructor(
        private val router: Router,
        private val actionRepository: ActionRepository,
        private val schedulers: RxSchedulers
) : MvpPresenter<ActionDetailView>() {

    lateinit var actionId: ActionId

    private var disposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        actionRepository
                .getActionObservable(actionId.id)
                .observeOn(schedulers.ui())
                .subscribe { viewState.showAction(it) }
    }

    fun onDeleteEventClick(event: Event) {
        actionRepository
                .getDeleteEventCompletable(actionId.id, event.id)
                .subscribe()
    }

    fun onCreateEventClick() {
        actionRepository
                .getInsertEventSingle(InsertEventData(actionId.id, 1, Calendar.getInstance()))
                .subscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}
