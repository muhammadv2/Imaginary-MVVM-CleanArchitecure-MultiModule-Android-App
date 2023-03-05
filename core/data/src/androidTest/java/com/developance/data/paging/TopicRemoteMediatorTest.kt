@file:OptIn(ExperimentalPagingApi::class)

package com.developance.data.paging

import android.content.Context
import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.developance.database.TopicsDatabase
import com.developance.database.model.TopicEntity
import com.developance.model.data.Topic
import com.developance.network.fake.FakeAndroidTestAssetManager
import com.developance.network.fake.FakeImaginaryNetworkDataSource
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * The goal of the RemoteMediator unit tests is to verify that
 * the load() function returns the correct MediatorResult.
 * The first case is when mockApi returns valid data. The load() function should return
 * MediatorResult.Success, and the endOfPaginationReached property should be false.
 * The second case is when mockApi returns a successful response, but the returned data is empty.
 * The load() function should return MediatorResult.Success,
 * and the endOfPaginationReached property should be true.
 * The third case is when mockApi throws an exception when fetching the data.
 * The load() function should return MediatorResult.Error.
 */
class TopicRemoteMediatorTest {

    private lateinit var fakeNetworkDataSource: FakeImaginaryNetworkDataSource

    private lateinit var mockDb: TopicsDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        mockDb = Room.inMemoryDatabaseBuilder(
            context,
            TopicsDatabase::class.java
        ).build()
        fakeNetworkDataSource =
            FakeImaginaryNetworkDataSource(
                UnconfinedTestDispatcher(),
                asset = FakeAndroidTestAssetManager(context)
            )
    }

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        // Add mock results for the API to return.
        val remoteMediator = TopicRemoteMediator(
            mockDb,
            fakeNetworkDataSource,
        )
        val pagingState = PagingState<Int, TopicEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue { result is RemoteMediator.MediatorResult.Success }
        assertFalse { (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached }
    }


    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() = runTest {
        // Set up failure message to throw exception from the mock API.
        fakeNetworkDataSource.failureMsg = "Throw test failure"
        val remoteMediator = TopicRemoteMediator(
            mockDb,
            fakeNetworkDataSource,
        )

        val pagingState = PagingState<Int, TopicEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue {result is RemoteMediator.MediatorResult.Error }
    }
    @After
    fun tearDown() {
        mockDb.clearAllTables()
        // Clear out failure message to default to the successful response.
        fakeNetworkDataSource.failureMsg = null
    }
}

