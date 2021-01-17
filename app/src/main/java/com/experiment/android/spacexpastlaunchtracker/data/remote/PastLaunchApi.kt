package com.experiment.android.spacexpastlaunchtracker.data.remote

import com.experiment.android.spacexpastlaunchtracker.models.response.PastLaunchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PastLaunchApi {
    @GET("past")
    suspend fun getPastLaunches(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("order") order: String
    ): List<PastLaunchResponse>

    @GET("past")
    suspend fun getAllPastLaunches(): List<PastLaunchResponse>

}