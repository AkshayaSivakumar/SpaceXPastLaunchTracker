package com.experiment.android.spacexpastlaunchtracker.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.experiment.android.spacexpastlaunchtracker.data.paging.PastLaunchesPagingSource
import com.experiment.android.spacexpastlaunchtracker.data.remote.PastLaunchApi
import com.experiment.android.spacexpastlaunchtracker.utils.constants.AppConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PastLaunchListRepository @Inject constructor(
    private var pastLaunchApi: PastLaunchApi
) {
    fun getPastLaunches() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 120,
                prefetchDistance = 5,
                initialLoadSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PastLaunchesPagingSource(pastLaunchApi, AppConstants.ITEMS_PER_PAGE)
            }
        ).liveData
}