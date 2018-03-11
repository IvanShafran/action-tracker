package me.shafran.actiontracker.ui.view.actions

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import me.shafran.actiontracker.R
import me.shafran.actiontracker.data.entity.Action

class ActionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)

    private val countTextView = itemView.findViewById<TextView>(R.id.countTextView)

    private val addActionEventButton = itemView.findViewById<View>(R.id.addActionEventButton)

    init {
        addActionEventButton.setOnClickListener { listener?.onAddActionEventClick(adapterPosition) }
        itemView.setOnClickListener { listener?.onOpenActionClick(adapterPosition) }
    }

    interface Listener {

        fun onAddActionEventClick(position: Int)

        fun onOpenActionClick(position: Int)
    }

    var listener: Listener? = null

    fun bind(action: Action) {
        nameTextView.text = action.name
        countTextView.text = action.events.size.toString()
    }
}
