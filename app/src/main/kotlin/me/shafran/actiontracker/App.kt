package me.shafran.actiontracker

import android.app.Application
import me.shafran.actiontracker.di.initializeToothpick

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initializeToothpick(this, isProduction())
    }

    private fun isProduction(): Boolean {
        return !BuildConfig.DEBUG
    }
}
