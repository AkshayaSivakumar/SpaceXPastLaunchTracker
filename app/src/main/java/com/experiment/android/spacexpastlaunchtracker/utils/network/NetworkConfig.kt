package com.experiment.android.spacexpastlaunchtracker.utils.network

import android.app.Application
import android.content.Context
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File

class NetworkConfig(private var context: Application) {
    fun getBaseUrl(): String = "https://api.spacexdata.com/v3/launches/"

    fun getCacheDir(): File = context.cacheDir

    fun getCacheSize(): Long = 10 * 1024 * 1024

    fun getTimeoutSeconds(): Long = 20

    fun ioScheduler(): Scheduler = Schedulers.io()

    fun mainThreadScheduler(): Scheduler = AndroidSchedulers.mainThread()
}