package com.experiment.android.spacexpastlaunchtracker.ui.launchdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LaunchDetailsViewModel : ViewModel() {

    private val _missionTitle = MutableLiveData<String>()
    val missionTitle: LiveData<String>
        get() = _missionTitle

    fun setTitle(title: String) {
        _missionTitle.value = title
    }
}