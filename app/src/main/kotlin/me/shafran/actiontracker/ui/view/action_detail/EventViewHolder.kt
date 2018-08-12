package me.shafran.actiontracker.ui.view.action_detail

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import me.shafran.actiontracker.R
import me.shafran.actiontracker.data.entity.Event
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val dateTextView: TextView = view.findViewById(R.id.dateTextView)

    private val deleteButton: ImageView = view.findViewById(R.id.deleteEventButton)

    init {
        deleteButton.setOnClickListener { listener?.onDeleteEventClick(adapterPosition) }
    }

    interface Listener {

        fun onDeleteEventClick(position: Int)
    }

    var listener: Listener? = null

    fun bind(event: Event) {
        dateTextView.text = formatDate(event.trackedDate)
    }

    private fun formatDate(calendar: Calendar): String {
        val format = SimpleDateFormat("dd-MM-yy HH:mm", Locale.US)
        return format.format(calendar.time)
    }
}
