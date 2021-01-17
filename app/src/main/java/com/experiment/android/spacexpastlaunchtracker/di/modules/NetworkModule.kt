package com.experiment.android.spacexpastlaunchtracker.di.modules

import android.app.Application
import com.experiment.android.spacexpastlaunchtracker.BuildConfig
import com.experiment.android.spacexpastlaunchtracker.data.remote.PastLaunchApi
import com.experiment.android.spacexpastlaunchtracker.di.scope.InternalApi
import com.experiment.android.spacexpastlaunchtracker.utils.network.NetworkConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Network module to provide network related instances
 */
@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    /**
     * Provides GSON Converter Factory instance
     */
    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }


    /**
     * Provides Network Configuration instance
     */
    @InternalApi
    @Provides
    fun providesNetworkConfig(context: Application): NetworkConfig {
        return NetworkConfig(context)
    }

    /**
     * Provides OkHttpCache instance
     */
    @InternalApi
    @Provides
    @Singleton
    fun providesOkHttpCache(
        @InternalApi
        networkConfig: NetworkConfig
    ): Cache {
        return Cache(networkConfig.getCacheDir(), networkConfig.getCacheSize())
    }

    /**
     * Provides Http Logging Interceptor instance
     */
    @InternalApi
    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return if (BuildConfig.DEBUG) {
            Timber.d("Http Logging Interceptor Enabled")
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        } else HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.NONE
        }
    }

    /**
     * Provides OkHttpClient instance
     */
    @InternalApi
    @Provides
    @Singleton
    fun providesOkHttpClient(
        @InternalApi
        networkConfig: NetworkConfig,
        @InternalApi
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @InternalApi
        cache: Cache
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(networkConfig.getTimeoutSeconds(), TimeUnit.SECONDS)
            .connectTimeout(networkConfig.getTimeoutSeconds(), TimeUnit.SECONDS)
            .cache(cache)

        return builder.build();
    }

    /**
     * Provides retrofit instance
     */
    @Provides
    @Singleton
    fun providesRetrofit(
        @InternalApi
        networkConfig: NetworkConfig,
        @InternalApi
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(networkConfig.getBaseUrl())
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    /**
     * Provides API service instance
     */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): PastLaunchApi {
        return retrofit.create(PastLaunchApi::class.java)
    }
}