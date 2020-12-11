package com.experiment.android.spacexpastlaunchtracker.models.response

import android.os.Parcelable
import com.experiment.android.spacexpastlaunchtracker.models.ViewType
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PastLaunchResponse(
    @SerializedName("flight_number")
    val flightNumber: Int = 0,
    @SerializedName("mission_name")
    val missionName: String = "",
    @SerializedName("launch_date_utc")
    val launchDateUtc: String = "",
    val rocket: Rocket? = Rocket(),
    @SerializedName("launch_site")
    val launchSite: LaunchSite? = LaunchSite(),
    @SerializedName("launch_success")
    val launchSuccess: Boolean = false,
    val links: Links? = Links(),
    val details: String? = ""
) : Parcelable

@Parcelize
data class Rocket(
    @SerializedName("rocket_name")
    val rocketName: String? = "",
    @SerializedName("rocket_type")
    var rocketType: String? = "",
) : Parcelable

@Parcelize
data class LaunchSite(
    @SerializedName("site_name")
    var siteName: String? = "",
    @SerializedName("site_name_long")
    var siteNameLong: String? = ""
) : Parcelable

@Parcelize
data class Links(
    @SerializedName("mission_patch")
    val missionPathImageUrl: String? = null,
    @SerializedName("reddit_campaign")
    val redditCampaignLink: String? = "",
    @SerializedName("reddit_media")
    val redditMediaLink: String? = "",
    @SerializedName("article_link")
    var articleLink: String? = "",
    @SerializedName("wikipedia")
    var wikiLink: String? = "",
    @SerializedName("video_link")
    var videoLink: String? = "",
    @SerializedName("youtube_id")
    var youtubeId: String? = ""
) : Parcelable, ViewType()