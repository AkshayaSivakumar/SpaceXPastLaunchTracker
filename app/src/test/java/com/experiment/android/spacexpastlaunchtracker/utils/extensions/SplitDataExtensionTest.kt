package com.experiment.android.spacexpastlaunchtracker.utils.extensions

import com.experiment.android.spacexpastlaunchtracker.models.MainDetailsModel
import com.experiment.android.spacexpastlaunchtracker.models.ViewType
import com.experiment.android.spacexpastlaunchtracker.models.response.PastLaunchResponse
import com.experiment.android.spacexpastlaunchtracker.utils.tests.TestUtils
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SplitDataExtensionTest {
    //GIVEN pastLaunchResponse
    private lateinit var pastLaunchResponseWithTestData: PastLaunchResponse

    @Before
    fun setUp() {
        pastLaunchResponseWithTestData = TestUtils.createTestData1()
    }

    /**
     * Test to check the conversion of {@link 'PastLaunchResponse'} to {@link 'MainDetailsModel'}
     * to display in the {@link 'LaunchDetailsFragment'} recycler view
     */
    @Test
    fun convertToMainDetailsModelTest_getPastLaunchResponse_returnMainDetailsModel() {
        //WHEN pastLaunchResponse converted/mapped to MainDetailsModel
        val mainDetailsModel =
            pastLaunchResponseWithTestData.asMainDetailsModel() as MainDetailsModel

        //THEN mainDetailsModel is created from pastLaunchResponse
        assertEquals(109, mainDetailsModel.flightNumber)
    }

    /**
     * Test to check the conversion of {@link 'PastLaunchResponse'} to {@link 'LinksModel'}
     * to display in the {@link 'LaunchDetailsFragment'} recycler view
     */
    @Test
    fun convertToLinksModelTest_getPastLaunchResponse_returnLinksModel() {
        val linksModel = pastLaunchResponseWithTestData.asLinksModel()

        assertEquals(TestUtils.linksTestData1, linksModel)
    }

    @Test
    fun arrayListAddItemTest_getItem_addToViewTypeArrayList() {
        val arrayList = ArrayList<ViewType>()

        arrayList.addItem(pastLaunchResponseWithTestData.asMainDetailsModel())
        arrayList.addItem(pastLaunchResponseWithTestData.asLinksModel())

        assert(arrayList.size > 0)
    }

}