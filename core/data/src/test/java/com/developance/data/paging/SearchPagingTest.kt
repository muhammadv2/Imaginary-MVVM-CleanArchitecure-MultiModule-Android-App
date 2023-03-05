package com.developance.data.paging

import androidx.paging.PagingSource
import com.developance.model.data.Photo
import com.developance.network.fake.FakeImaginaryNetworkDataSource
import com.developance.network.model.asExternalModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertTrue

/**
 * Unit tests for your PagingSource classes involve setting up the PagingSource
 * instance and verifying that the load() function returns the correct paged
 * data based on a LoadParams argument
 */
class SearchPagingTest {

    private val fakeNetworkDataSource = FakeImaginaryNetworkDataSource(
        UnconfinedTestDispatcher()
    )

    private var page = 1

    @Test
    fun searchPaging_loadReturnsPageWhenOnSuccessfulLoadOfItemKeyedData() = runTest {

        val pagingSource =
            SearchPagingSource(fakeNetworkDataSource, "") as PagingSource<Int, Photo>


        assertThat(
            PagingSource.LoadResult.Page(
                data = fakeNetworkDataSource
                    .fetchPhotosByQuery("", page).result
                    .map { it.asExternalModel() },
                prevKey = null,
                nextKey = page + 1
            )
        ).isEqualTo(
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun searchPaging_refreshLoadReturnsErrorResultWhenErrorOccurs() = runTest {
        // Set up failure message to throw exception from the mock API.
        fakeNetworkDataSource.failureMsg = "Throw Test Error"

        val pagingSource =
            SearchPagingSource(fakeNetworkDataSource, "") as PagingSource<Int, Photo>

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )
        assertTrue { result is PagingSource.LoadResult.Error }
    }

}