package com.experiment.android.spacexpastlaunchtracker.utils.custom

import com.experiment.android.spacexpastlaunchtracker.models.MainDetailsModel
import com.experiment.android.spacexpastlaunchtracker.models.ViewType
import com.experiment.android.spacexpastlaunchtracker.models.response.Links
import com.experiment.android.spacexpastlaunchtracker.models.response.PastLaunchResponse
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SplitDataExtensionTest {
    //GIVEN pastLaunchResponse
    private val pastLaunchResponse = PastLaunchResponse()

    /**
     * Test to check the conversion of {@link 'PastLaunchResponse'} to {@link 'MainDetailsModel'}
     * to display in the {@link 'LaunchDetailsFragment'} recycler view
     */
    @Test
    fun convertPastLaunchResponseToMainDetailsModelTest_getPastLaunchResponse_returnMainDetailsModel() {
        //WHEN pastLaunchResponse converted/mapped to MainDetailsModel
        val mainDetailsModel = pastLaunchResponse.asMainDetailsModel()

        //THEN mainDetailsModel is created from pastLaunchResponse
        assertEquals(
            MainDetailsModel(
                flightNumber = 0,
                launchDateUtc = "",
                launchSuccess = false,
                launchSiteNameLong = "",
                rocketType = "",
                rocketName = ""
            ), mainDetailsModel
        )
    }

    /**
     * Test to check the conversion of {@link 'PastLaunchResponse'} to {@link 'LinksModel'}
     * to display in the {@link 'LaunchDetailsFragment'} recycler view
     */
    @Test
    fun convertPastLaunchResponseToLinksModelTest_getPastLaunchResponse_returnLinksModel() {
        val linksModel = pastLaunchResponse.asLinksModel()

        assertEquals(Links(), linksModel)
    }

    @Test
    fun arrayListAddItemTest_getItem_addToViewTypeArrayList() {
        val arrayList = ArrayList<ViewType>()

        arrayList.addItem(pastLaunchResponse.asMainDetailsModel())
        arrayList.addItem(pastLaunchResponse.asLinksModel())

        assert(arrayList.size > 0)
    }
}