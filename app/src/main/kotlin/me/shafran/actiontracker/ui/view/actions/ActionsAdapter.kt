package me.shafran.actiontracker.ui.view.actions

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import me.shafran.actiontracker.R
import me.shafran.actiontracker.data.entity.Action

class ActionsAdapter : RecyclerView.Adapter<ActionViewHolder>(), ActionViewHolder.Listener {

    interface Listener {

        fun onCreateEventClick(action: Action)

        fun onOpenActionClick(action: Action)
    }

    var actions: List<Action> = listOf()

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_action, parent, false)

        val viewHolder = ActionViewHolder(view)
        viewHolder.listener = this

        return viewHolder
    }

    override fun getItemCount() = actions.size

    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        holder.bind(actions[position])
    }

    override fun onCreateEventClick(position: Int) {
        if (position in actions.indices) {
            listener?.onCreateEventClick(actions[position])
        }
    }

    override fun onOpenActionClick(position: Int) {
        if (position in actions.indices) {
            listener?.onOpenActionClick(actions[position])
        }
    }
}
