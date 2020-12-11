package com.experiment.android.spacexpastlaunchtracker.data.paging

import androidx.paging.PagingSource
import com.experiment.android.spacexpastlaunchtracker.data.remote.PastLaunchApi
import com.experiment.android.spacexpastlaunchtracker.models.response.PastLaunchResponse
import com.experiment.android.spacexpastlaunchtracker.utils.constants.AppConstants
import okio.IOException
import retrofit2.HttpException

class PastLaunchesPagingSource(
    private val pastLaunchApi: PastLaunchApi,
    private val limit: Int
) : PagingSource<Int, PastLaunchResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PastLaunchResponse> {
        val position = params.key ?: AppConstants.OFFSET
        return try {
            val response = pastLaunchApi.getPastLaunches(limit, position, AppConstants.SORT_ORDER)
            LoadResult.Page(
                data = response,
                prevKey = getPrevKey(position, limit),
                nextKey = if (response.isEmpty()) null else position + limit
            )

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    private fun getPrevKey(position: Int, limit: Int): Int? {
        return if (position == AppConstants.OFFSET)
            null
        else {
            if ((position - limit) < 0)
                0
            else
                position - limit
        }
    }
}
