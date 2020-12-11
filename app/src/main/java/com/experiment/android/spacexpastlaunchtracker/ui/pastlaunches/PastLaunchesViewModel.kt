package com.experiment.android.spacexpastlaunchtracker.ui.pastlaunches

import android.app.Application
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.experiment.android.spacexpastlaunchtracker.data.repo.PastLaunchListRepository
import com.experiment.android.spacexpastlaunchtracker.models.response.PastLaunchResponse

class PastLaunchesViewModel @ViewModelInject constructor(
    application: Application,
    private val repository: PastLaunchListRepository,
    @Assisted state: SavedStateHandle
) : AndroidViewModel(application) {

    private val _pastLaunchListFetchStatus = state.getLiveData(CURRENT_REQUEST_STATE, false)

    private val _navigateToDetails = MutableLiveData<PastLaunchResponse>()
    val navigateToDetails: LiveData<PastLaunchResponse>
        get() = _navigateToDetails

    val pastLaunchList = _pastLaunchListFetchStatus.switchMap { _ ->
        repository.getPastLaunches().cachedIn(viewModelScope)
    }

    init {
        _pastLaunchListFetchStatus.value = true
    }

    fun navigateToDetailsFragment(pastLaunchResponse: PastLaunchResponse) {
        _navigateToDetails.value = pastLaunchResponse
    }

    fun navigateToDetailsFragmentComplete() {
        _navigateToDetails.value = null
    }

    companion object {
        private const val CURRENT_REQUEST_STATE = "current_state"
    }

}