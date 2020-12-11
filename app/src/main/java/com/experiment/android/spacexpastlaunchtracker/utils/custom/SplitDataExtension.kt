package com.experiment.android.spacexpastlaunchtracker.utils.custom

import com.experiment.android.spacexpastlaunchtracker.models.MainDetailsModel
import com.experiment.android.spacexpastlaunchtracker.models.ViewType
import com.experiment.android.spacexpastlaunchtracker.models.response.Links
import com.experiment.android.spacexpastlaunchtracker.models.response.PastLaunchResponse

/**
 * Extension function to split PastLaunchResponse as Links type for easy population in recycler view in LaunchDetails Fragment
 */
fun PastLaunchResponse.asLinksModel(): ViewType {
    return Links(
        missionPathImageUrl = links?.missionPathImageUrl,
        redditCampaignLink = links?.redditCampaignLink ?: "",
        redditMediaLink = links?.redditMediaLink ?: "",
        articleLink = links?.articleLink ?: "",
        wikiLink = links?.wikiLink ?: "",
        videoLink = links?.videoLink ?: "",
        youtubeId = links?.youtubeId ?: ""
    )
}

/**
 * Extension function to split PastLaunchResponse as Details type for easy population in recycler view in LaunchDetails Fragment
 */
fun PastLaunchResponse.asMainDetailsModel(): ViewType {
    return MainDetailsModel(
        flightNumber = flightNumber,
        launchDateUtc = launchDateUtc,
        rocketName = rocket?.rocketName ?: "",
        rocketType = rocket?.rocketType ?: "",
        launchSiteNameLong = launchSite?.siteNameLong ?: "",
        launchSuccess = launchSuccess
    )
}

/**
 * Extension function to add items to ArrayList<ViewType>
 */
fun ArrayList<ViewType>.addItem(viewType: ViewType) {
    viewType?.let {
        add(it)
    }
}