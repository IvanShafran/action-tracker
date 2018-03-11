package me.shafran.actiontracker.rx

import io.reactivex.Scheduler

interface RxSchedulers {

    fun ui(): Scheduler

    fun io(): Scheduler

    fun computation(): Scheduler
}
