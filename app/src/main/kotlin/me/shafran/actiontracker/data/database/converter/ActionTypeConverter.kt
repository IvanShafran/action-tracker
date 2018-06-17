package me.shafran.actiontracker.data.database.converter

import me.shafran.actiontracker.data.entity.ActionType

object ActionTypeConverter {

    fun getStringRepresentation(actionType: ActionType): String {
        return actionType.uniqueName
    }

    fun getActionTypeFromStringRepresentation(value: String): ActionType {
        for (type in ActionType.values()) {
            if (type.uniqueName == value) {
                return type
            }
        }

        throw IllegalArgumentException("Unknown action type value")
    }
}
