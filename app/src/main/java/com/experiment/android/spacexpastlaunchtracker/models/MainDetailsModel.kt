package com.experiment.android.spacexpastlaunchtracker.models

data class MainDetailsModel(
    val flightNumber: Int,
    val launchDateUtc: String,
    val rocketName: String,
    val rocketType: String,
    val launchSiteNameLong: String,
    val launchSuccess: Boolean,
) : ViewType()