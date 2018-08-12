package me.shafran.actiontracker.ui.view.action_detail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import me.shafran.actiontracker.R
import me.shafran.actiontracker.data.entity.Event

class EventsAdapter : RecyclerView.Adapter<EventViewHolder>(), EventViewHolder.Listener {

    interface Listener {
        fun onDeleteEventClick(event: Event)
    }

    private var events: List<Event> = listOf()

    var listener: Listener? = null

    fun showEvents(events: List<Event>) {
        this.events = events
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_event, parent, false)

        val viewHolder = EventViewHolder(view)
        viewHolder.listener = this

        return viewHolder
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun onDeleteEventClick(position: Int) {
        if (position in events.indices) {
            listener?.onDeleteEventClick(events[position])
        }
    }
}
