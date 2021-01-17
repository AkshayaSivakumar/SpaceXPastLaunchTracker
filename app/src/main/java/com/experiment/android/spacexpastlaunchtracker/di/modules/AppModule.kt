package com.experiment.android.spacexpastlaunchtracker.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 * AppModule to provide application related instances
 */
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    /**
     * Provides application context as Context
     */
    @Provides
    @Singleton
    fun providesContext(appContext: Application): Context = appContext.applicationContext

}