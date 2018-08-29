package me.shafran.actiontracker.ui.view.action_detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import kotlinx.android.synthetic.main.fragment_action_detail.*
import me.shafran.actiontracker.R
import me.shafran.actiontracker.data.entity.Event
import me.shafran.actiontracker.data.repository.datasource.ActionId
import me.shafran.actiontracker.di.Scopes
import me.shafran.actiontracker.ui.presentation.action_detail.ActionDetailPresenter
import me.shafran.actiontracker.ui.presentation.action_detail.ActionDetailView
import me.shafran.actiontracker.ui.view.base.BaseFragment
import toothpick.Toothpick
import java.util.Calendar
import java.util.Date

typealias CalendarEvent = com.github.sundeepk.compactcalendarview.domain.Event

class ActionDetailFragment : BaseFragment(), ActionDetailView, EventsAdapter.Listener {

    companion object {
        private const val ACTION_ID_KEY = "ACTION_ID_KEY"

        fun newInstance(actionId: Long): Fragment {
            val fragment = ActionDetailFragment()

            val arguments = Bundle()
            arguments.putLong(ACTION_ID_KEY, actionId)
            fragment.arguments = arguments

            return fragment
        }
    }

    @InjectPresenter
    lateinit var presenter: ActionDetailPresenter

    @ProvidePresenter
    fun providePresenter(): ActionDetailPresenter {
        val presenter = Toothpick
                .openScope(Scopes.ROOT_SCOPE)
                .getInstance(ActionDetailPresenter::class.java)

        val actionId = requireArguments().getLong(ACTION_ID_KEY)
        presenter.actionId = ActionId(actionId)

        return presenter
    }

    private val adapter: EventsAdapter by lazy { EventsAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_action_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        setupRecyclerView()

        createEventButton.setOnClickListener { presenter.onCreateEventClick() }

        setupCalendarView()
    }

    private fun setupCalendarView() {
        calendarView.setListener(object : CompactCalendarView.CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date) = onDayChanged(dateClicked)

            override fun onMonthScroll(firstDayOfNewMonth: Date) = onDayChanged(firstDayOfNewMonth)
        })
        onDayChanged(Date())
    }

    private fun onDayChanged(date: Date) {
        val calendar = Calendar.getInstance()
        calendar.time = date
        presenter.onDayChanged(calendar)
    }

    private fun setupRecyclerView() {
        eventsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            this@apply.adapter = this@ActionDetailFragment.adapter
        }

        adapter.listener = this
    }

    override fun showEventsOnCalendar(events: List<Event>) {
        val color: Int = ContextCompat.getColor(requireContext(), R.color.secondaryDarkColor)
        val calendarEvents: List<CalendarEvent> = events.map {
            CalendarEvent(color, it.trackedDate.timeInMillis)
        }
        calendarView.addEvents(calendarEvents)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_detail_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuActionDelete -> {
                presenter.onDeleteActionClick()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showDayEvents(events: List<Event>) = adapter.showEvents(events)

    override fun showConfirmDeleteActionDialog() {
        val dialog = AlertDialog
                .Builder(requireContext())
                .setPositiveButton(android.R.string.yes) { _, _ -> presenter.onDeleteActionConfirmedClick() }
                .setNegativeButton(android.R.string.no, null)
                .setMessage(R.string.action_delete_confirm_dialog_message)
                .create()
        dialog.show()
    }

    override fun onDeleteEventClick(event: Event) = presenter.onDeleteEventClick(event)
}
