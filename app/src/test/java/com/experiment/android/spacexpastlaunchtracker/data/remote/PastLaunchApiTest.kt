package com.experiment.android.spacexpastlaunchtracker.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.experiment.android.spacexpastlaunchtracker.utils.constants.Constants
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers
import org.hamcrest.core.IsNull
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class PastLaunchApiTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: PastLaunchApi

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PastLaunchApi::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun getPastLaunches() = runBlocking {
        enqueueResponse("pastlaunches.json")
        val pastLaunchesList =
            service.getPastLaunches(10, Constants.DEFAULT_OFFSET, Constants.SORT_ORDER)

        val pastLaunchSingleResponse = pastLaunchesList[1]

        println(pastLaunchSingleResponse.toString())

        Assert.assertThat(pastLaunchesList, IsNull.notNullValue())
        Assert.assertThat(pastLaunchesList.size, CoreMatchers.`is`(10))
        Assert.assertThat(pastLaunchesList[0].flightNumber, CoreMatchers.`is`(109))
        Assert.assertThat(pastLaunchesList[0].missionName, CoreMatchers.`is`("Starlink-15 (v1.0)"))
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(source.readString(Charsets.UTF_8)))
    }
}