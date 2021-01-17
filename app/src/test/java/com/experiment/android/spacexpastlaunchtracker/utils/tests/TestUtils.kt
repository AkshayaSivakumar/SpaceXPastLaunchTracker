package com.experiment.android.spacexpastlaunchtracker.utils.tests

import com.experiment.android.spacexpastlaunchtracker.models.response.LaunchSite
import com.experiment.android.spacexpastlaunchtracker.models.response.Links
import com.experiment.android.spacexpastlaunchtracker.models.response.PastLaunchResponse
import com.experiment.android.spacexpastlaunchtracker.models.response.Rocket

object TestUtils {

    init {
        createLaunchDataListTestData()
    }

    fun createLaunchDataListTestData(): List<PastLaunchResponse> {

        val testData = ArrayList<PastLaunchResponse>()
        testData.add(createTestData1())
        testData.add(createTestData2())

        return testData
    }

    fun createTestData1(): PastLaunchResponse {
        val rocketTestData1 = Rocket(rocketName = "Falcon 9", rocketType = "FT")

        val launchSiteTestData1 = LaunchSite(
            siteName = "CCAFS SLC 40",
            siteNameLong = "Cape Canaveral Air Force Station Space Launch Complex 40"
        )

        return PastLaunchResponse(
            flightNumber = 109,
            missionName = "Starlink-15 (v1.0)",
            launchDateUtc = "2020-10-24T15:31:00.000Z",
            rocket = rocketTestData1,
            launchSite = launchSiteTestData1,
            launchSuccess = true,
            links = linksTestData1,
            details = ""
        )
    }

    val linksTestData1 = Links(
        missionPathImageUrl = "https://images2.imgbox.com/d2/3b/bQaWiil0_o.png",
        redditCampaignLink = "https://www.reddit.com/r/spacex/comments/i63bst/starlink_general_discussion_and_deployment_thread/",
        redditMediaLink = "",
        articleLink = "",
        wikiLink = "https://en.wikipedia.org/wiki/Starlink",
        videoLink = "https://youtu.be/J442-ti-Dhg",
        youtubeId = "J442-ti-Dhg"
    )

    private fun createTestData2(): PastLaunchResponse {
        val rocketTestData2 = Rocket(rocketName = "Falcon 9", rocketType = "FT")

        val launchSiteTestData2 = LaunchSite(
            siteName = "VAFB SLC 4E",
            siteNameLong = "Vandenberg Air Force Base Space Launch Complex 4E"
        )

        val linksTestData2 = Links(
            missionPathImageUrl = "",
            redditCampaignLink = "https://www.reddit.com/r/spacex/comments/jkk93v/sentinel6_michael_freilich_launch_campaign_thread/",
            redditMediaLink = "https://www.reddit.com/r/spacex/comments/jyd67q/rspacex_sentinel6_media_thread_photographer/",
            articleLink = "https://spaceflightnow.com/2020/11/21/international-satellite-launches-to-extend-measurements-of-sea-level-rise/",
            wikiLink = "https://en.wikipedia.org/wiki/Sentinel-6_Michael_Frielich",
            videoLink = "https://youtu.be/aVFPzTDCihQ",
            youtubeId = "aVFPzTDCihQ"
        )

        return PastLaunchResponse(
            flightNumber = 108,
            missionName = "Sentinel-6 Michael Freilich",
            launchDateUtc = "2020-11-21T17:17:00.000Z",
            rocket = rocketTestData2,
            launchSite = launchSiteTestData2,
            launchSuccess = true,
            links = linksTestData2,
            details = "SpaceX will launch Sentinel-6 Michael Freilich into low Earth orbit for NASA, NOAA, ESA, and the European Organization for the Exploitation of Meteorological Satellites aboard a Falcon 9 from SLC-4E, Vandenberg Air Force Station. Sentinel-6(A) is an ocean observation satellite providing radar ocean surface altimetry data and also atmospheric temperature profiles as a secondary mission. The booster for this mission is will land at LZ-4."
        )
    }

}