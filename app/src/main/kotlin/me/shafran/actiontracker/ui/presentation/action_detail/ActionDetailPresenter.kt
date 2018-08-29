package me.shafran.actiontracker.ui.presentation.action_detail

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.CompositeDisposable
import me.shafran.actiontracker.data.entity.Event
import me.shafran.actiontracker.data.repository.ActionDeletedData
import me.shafran.actiontracker.data.repository.ActionExistData
import me.shafran.actiontracker.data.repository.ActionRepository
import me.shafran.actiontracker.data.repository.datasource.ActionId
import me.shafran.actiontracker.data.repository.datasource.InsertEventData
import me.shafran.actiontracker.domain.ActionDetailInteractor
import me.shafran.actiontracker.rx.RxSchedulers
import me.shafran.actiontracker.rx.addToCompositeDisposable
import ru.terrakok.cicerone.Router
import java.util.Calendar
import javax.inject.Inject

@InjectViewState
class ActionDetailPresenter @Inject constructor(
        private val router: Router,
        private val actionDetailInteractor: ActionDetailInteractor,
        private val actionRepository: ActionRepository,
        private val schedulers: RxSchedulers
) : MvpPresenter<ActionDetailView>() {

    lateinit var actionId: ActionId

    private val compositeDisposable = CompositeDisposable()

    private val titleBuilder = ActionDetailTitleBuilder()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        loadAllActionEvents()
        loadFilteredEvents()
    }

    private fun loadAllActionEvents() {
        actionRepository
                .getActionSingle(actionId.id)
                .observeOn(schedulers.ui())
                .subscribe { actionData ->
                    when (actionData) {
                        is ActionDeletedData -> {
                            // Do nothing. Handle on loadFilteredEvents
                        }
                        is ActionExistData -> {
                            titleBuilder.actionName = actionData.action.name
                            showTitleIfReady()
                            viewState.showEventsOnCalendar(actionData.action.events)
                        }
                    }
                }
                .addToCompositeDisposable(compositeDisposable)
    }

    private fun loadFilteredEvents() {
        actionDetailInteractor
                .getFilteredActionObservable(actionId.id)
                .observeOn(schedulers.ui())
                .subscribe { actionData ->
                    when (actionData) {
                        is ActionDeletedData -> router.exit()
                        is ActionExistData -> viewState.showDayEvents(actionData.action.events)
                    }
                }
                .addToCompositeDisposable(compositeDisposable)
    }

    fun onDayChanged(date: Calendar) {
        actionDetailInteractor.setFilterDate(date)

        titleBuilder.date = date
        showTitleIfReady()
    }

    private fun showTitleIfReady() {
        if (titleBuilder.isReadyToShow()) {
            viewState.showTitle(titleBuilder.getTitle())
        }
    }

    fun onDeleteActionClick() {
        viewState.showConfirmDeleteActionDialog()
    }

    fun onDeleteActionConfirmedClick() {
        actionRepository
                .getDeleteActionCompletable(actionId.id)
                .subscribe()
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
        compositeDisposable.dispose()
    }
}
