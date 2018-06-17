package me.shafran.actiontracker.di

import android.content.Context
import me.shafran.actiontracker.rx.RxSchedulers
import me.shafran.actiontracker.rx.RxSchedulersImpl
import toothpick.config.Module

class RootModule(context: Context) : Module() {

    init {
        bind(Context::class.java).toInstance(context)
        bind(RxSchedulers::class.java).to(RxSchedulersImpl::class.java).singletonInScope()
    }
}
