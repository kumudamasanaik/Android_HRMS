package com.hrms.hrmsandroidapp.scheduler

import io.reactivex.Scheduler

interface BaseScheduler {
    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler
}