package me.shafran.actiontracker.di

import android.database.sqlite.SQLiteOpenHelper
import me.shafran.actiontracker.data.database.ActionTrackerDbHelper
import me.shafran.actiontracker.data.database.datasource.DbActionDataSource
import me.shafran.actiontracker.data.database.holder.DatabaseHolder
import me.shafran.actiontracker.data.database.holder.DatabaseHolderImpl
import me.shafran.actiontracker.data.repository.ActionRepository
import me.shafran.actiontracker.data.repository.ActionRepositoryImpl
import me.shafran.actiontracker.data.repository.datasource.ActionDataSource
import toothpick.config.Module

class DataModule : Module() {

    init {
        bind(SQLiteOpenHelper::class.java).to(ActionTrackerDbHelper::class.java).singletonInScope()
        bind(DatabaseHolder::class.java).to(DatabaseHolderImpl::class.java).singletonInScope()
        bind(ActionDataSource::class.java).to(DbActionDataSource::class.java).singletonInScope()
        bind(ActionRepository::class.java).to(ActionRepositoryImpl::class.java).singletonInScope()
    }
}
