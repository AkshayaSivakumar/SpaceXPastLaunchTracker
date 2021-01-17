package com.experiment.android.spacexpastlaunchtracker.data.source

import androidx.paging.PagingSource
import com.experiment.android.spacexpastlaunchtracker.data.remote.PastLaunchApi
import com.experiment.android.spacexpastlaunchtracker.models.response.PastLaunchResponse
import com.experiment.android.spacexpastlaunchtracker.utils.constants.Constants
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

/**
 * Paging Data Source Class
 * Provides paged data to the repository
 */

class PastLaunchesPagingDataSource @Inject constructor(
    private val pastLaunchApi: PastLaunchApi,
) : PagingSource<Int, PastLaunchResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PastLaunchResponse> {
        val position = params.key ?: Constants.DEFAULT_OFFSET
        return try {
            val response = pastLaunchApi.getPastLaunches(
                Constants.ITEMS_PER_PAGE,
                position,
                Constants.SORT_ORDER
            )
            LoadResult.Page(
                data = response,
                prevKey = getPrevKey(position, Constants.ITEMS_PER_PAGE),
                nextKey = if (response.isEmpty()) null else position + Constants.ITEMS_PER_PAGE
            )

        } catch (exception: IOException) {
            /**
             * Network Errors
             */
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            /**
             * Other http errors
             */
            LoadResult.Error(exception)
        }
    }

    /**
     * Method to set previous page key using previous offset and limit
     * As page number is not provided by the API, calculate the pages using previous offset and limit
     *
     * @param limit -> number of items to be fetched
     * @param position -> provides the previous offset value
     */

    private fun getPrevKey(position: Int, limit: Int): Int? {
        return if (position == Constants.DEFAULT_OFFSET)
            null
        else {
            if ((position - limit) < 0)
                0
            else
                position - limit
        }
    }
}
