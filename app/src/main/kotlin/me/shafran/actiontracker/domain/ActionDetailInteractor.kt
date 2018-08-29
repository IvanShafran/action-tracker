package me.shafran.actiontracker.domain

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import me.shafran.actiontracker.data.entity.Event
import me.shafran.actiontracker.data.repository.ActionData
import me.shafran.actiontracker.data.repository.ActionDeletedData
import me.shafran.actiontracker.data.repository.ActionExistData
import me.shafran.actiontracker.data.repository.ActionRepository
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ActionDetailInteractor @Inject constructor(
        private val repository: ActionRepository
) {

    companion object {
        private val NOT_DAY_FIELDS = listOf(
                Calendar.MILLISECOND,
                Calendar.SECOND,
                Calendar.MINUTE,
                Calendar.HOUR
        )
    }

    private val filterSubject: Subject<Calendar> = BehaviorSubject.create()

    /**
     * Set filter date for [getFilteredActionObservable].
     * Only year, month, day matters.
     */
    fun setFilterDate(date: Calendar) {
        filterSubject.onNext(ensureDateIsDay(date))
    }

    /**
     * Get observable which emits [ActionData] filtered by date passed in [setFilterDate].
     * It emits data if action changed or filter date changed.
     * If [ActionData] is [ActionDeletedData], it keeps data as is.
     */
    fun getFilteredActionObservable(actionId: Long): Observable<ActionData> {
        return Observable
                .combineLatest(
                        filterSubject,
                        repository.getActionObservable(actionId),
                        BiFunction { date, actionData -> filterByDateIfNeeded(date, actionData) }
                )
    }

    private fun filterByDateIfNeeded(date: Calendar, actionData: ActionData): ActionData {
        if (actionData is ActionDeletedData) {
            return actionData
        } else if (actionData is ActionExistData) {
            val action = actionData.action
            val filteredAction = action.copy(events = filterByDate(action.events, date))
            return ActionExistData(filteredAction)
        } else {
            throw IllegalStateException("Unknown action data type")
        }
    }

    private fun filterByDate(events: List<Event>, date: Calendar): List<Event> {
        val nextDate = getNextDate(date)
        return events.filter { date <= it.trackedDate && it.trackedDate < nextDate }
    }

    private fun getNextDate(date: Calendar): Calendar {
        val nextDate = Calendar.getInstance()
        nextDate.timeInMillis = date.timeInMillis + TimeUnit.DAYS.toMillis(1)
        return nextDate
    }

    private fun ensureDateIsDay(date: Calendar): Calendar {
        val dayDate = Calendar.getInstance().apply { timeInMillis = date.timeInMillis }

        for (field in NOT_DAY_FIELDS) {
            dayDate.set(field, 0)
        }

        return dayDate
    }
}
