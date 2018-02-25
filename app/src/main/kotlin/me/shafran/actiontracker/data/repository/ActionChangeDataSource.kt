package me.shafran.actiontracker.data.repository

import java.util.Calendar


interface ActionChangeDataSource {

    class InsertActionParams(val name: String)

    fun insertAction(insertActionParams: InsertActionParams)


    class InsertActionEventParams(val actionId: Long, val trackedDate: Calendar)

    fun insertActionEvent(insertActionEventParams: InsertActionEventParams)


    class UpdateActionParams(val actionId: Long, val name: String)

    fun updateAction(updateActionParams: UpdateActionParams)


    fun deleteAction(actionId: Long)

    fun deleteActionEvent(actionEventId: Long)

}
