package com.experiment.android.spacexpastlaunchtracker.ui.pastlaunches

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.experiment.android.spacexpastlaunchtracker.data.repo.PastLaunchListRepository
import com.experiment.android.spacexpastlaunchtracker.models.response.PastLaunchResponse
import com.experiment.android.spacexpastlaunchtracker.utils.custom.SingleLiveEvent
import kotlinx.coroutines.flow.Flow


class PastLaunchesViewModel @ViewModelInject constructor(
    private val repository: PastLaunchListRepository,
    @Assisted state: SavedStateHandle
) : ViewModel() {

    private val navigateToDetailsScreen = SingleLiveEvent<PastLaunchResponse>()

    fun navigateToDetailsScreen(): SingleLiveEvent<PastLaunchResponse> {
        return navigateToDetailsScreen
    }

    fun triggerNavigation(pastLaunchResponse: PastLaunchResponse) {
        navigateToDetailsScreen.value = pastLaunchResponse
    }

    fun getPastLaunchesListAsFlow(): Flow<PagingData<PastLaunchResponse>> {
        return repository.getPastLaunchesAsFlow().cachedIn(viewModelScope)
    }

    /*private val _navigateToDetails = MutableLiveData<PastLaunchResponse>()
    val navigateToDetails: LiveData<PastLaunchResponse>
        get() = _navigateToDetails


    fun navigateToDetailsFragment(pastLaunchResponse: PastLaunchResponse) {
        _navigateToDetails.value = pastLaunchResponse
    }

    fun navigateToDetailsFragmentComplete() {
        _navigateToDetails.value = null
    }

    private val _pastLaunchListFetchStatus = state.getLiveData(CURRENT_REQUEST_STATE, false)

    val pastLaunchListAsLiveData = _pastLaunchListFetchStatus.switchMap { _ ->
        repository.getPastLaunchesAsLiveData().cachedIn(viewModelScope)
    }

    init {
        _pastLaunchListFetchStatus.value = true
    }

    companion object {
        private const val CURRENT_REQUEST_STATE = "current_state"
    }*/

}