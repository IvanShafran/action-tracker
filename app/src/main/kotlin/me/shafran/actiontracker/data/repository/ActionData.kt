package me.shafran.actiontracker.data.repository

import me.shafran.actiontracker.data.entity.Action

sealed class ActionData

class ActionExistData(val action: Action) : ActionData()

class ActionDeletedData(val actionId: Long) : ActionData()
