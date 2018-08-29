package me.shafran.actiontracker.ui.presentation.action_detail

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ActionDetailTitleBuilder(var date: Calendar? = null, var actionName: String? = null) {

    fun isReadyToShow(): Boolean {
        return date != null && actionName != null
    }

    fun getTitle(): String {
        checkForReadyToShow()
        return actionName + " " + getDateString()
    }

    private fun checkForReadyToShow() {
        if (!isReadyToShow()) {
            throw IllegalStateException("Not ready to show")
        }
    }

    private fun getDateString(): String {
        date?.let {
            val format = SimpleDateFormat("yyyy.MM.dd", Locale.US)
            return format.format(it.time)

        } ?: throw IllegalStateException("")
    }
}
