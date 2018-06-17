package me.shafran.actiontracker.data.repository.datasource

class ActionDataSourceException(
        message: String,
        cause: Throwable
) : Exception(message, cause)
