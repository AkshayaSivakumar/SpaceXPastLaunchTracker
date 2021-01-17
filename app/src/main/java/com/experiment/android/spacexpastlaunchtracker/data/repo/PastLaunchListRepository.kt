package com.experiment.android.spacexpastlaunchtracker.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.experiment.android.spacexpastlaunchtracker.data.source.PastLaunchesPagingDataSource
import com.experiment.android.spacexpastlaunchtracker.utils.constants.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PastLaunchListRepository @Inject constructor(
    private var pastLaunchesPagingDataSource: PastLaunchesPagingDataSource
) {
    fun getPastLaunchesAsLiveData() =
        Pager(
            config = PagingConfig(
                pageSize = Constants.ITEMS_PER_PAGE,
                maxSize = Constants.MAX_SIZE,
                prefetchDistance = Constants.FETCH_DISTANCE,
                initialLoadSize = Constants.ITEMS_PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                pastLaunchesPagingDataSource
            }
        ).liveData

    fun getPastLaunchesAsFlow() =
        Pager(
            config = PagingConfig(
                pageSize = Constants.ITEMS_PER_PAGE,
                maxSize = Constants.MAX_SIZE,
                prefetchDistance = Constants.FETCH_DISTANCE,
                initialLoadSize = Constants.ITEMS_PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { pastLaunchesPagingDataSource }
        ).flow
}