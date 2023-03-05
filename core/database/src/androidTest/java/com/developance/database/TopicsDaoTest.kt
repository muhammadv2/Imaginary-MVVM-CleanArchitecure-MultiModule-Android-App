package com.developance.database

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.developance.database.dao.TopicsDao
import com.developance.database.model.PartialTopicEntity
import com.developance.database.model.TopicEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class TopicsDaoTest {
    private lateinit var topicDao: TopicsDao
    private lateinit var db: TopicsDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            TopicsDatabase::class.java
        ).build()
        topicDao = db.topicDao
    }

    @Test
    fun topicsDao_insert_items_then_retrieve_using_paging_source() = runTest {
        val topicEntities = listOf(
            testTopicEntity(
                id = "1",
                name = "1"
            ),
            testTopicEntity(
                id = "2",
                name = "2"
            ),
        )

        topicDao.insertOrIgnoreTopics(
            topics = topicEntities
        )

        val topics = topicDao.getPagingSource()

        val actual = topics
            .load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            ) as? PagingSource.LoadResult.Page
        val expected = PagingSource.LoadResult.Page(
            data = topicEntities,
            prevKey = null,
            nextKey = topicEntities[1].id.toInt()
        ).data
        assertThat(actual!!.data).isEqualTo(expected)
    }

    @Test
    fun topicsDao_insert_favorite_items() = runTest {
        val topicEntities = listOf(
            testTopicEntity(
                id = "1",
                name = "1",
                isFavorite = true
            ),
            testTopicEntity(
                id = "2",
                name = "2",
                isFavorite = false
            ),
        )

        topicDao.insertOrIgnoreTopics(
            topics = topicEntities
        )

        topicDao.favoritedTopicsStream()
            .test {
                assertThat(awaitItem().size).isEqualTo(1)
            }

    }

    @Test
    fun topicsDao_update_partial_item() = runTest {
        val topicEntities = listOf(
            testTopicEntity(
                id = "1",
                name = "updated",
                isFavorite = false
            )
        )

        topicDao.insertOrIgnoreTopics(
            topics = topicEntities
        )

        val updatedTopicEntity = PartialTopicEntity("1", true)

        topicDao.updateTopic(partialtopic = updatedTopicEntity)

        topicDao.favoritedTopicsStream()
            .test {
                assertThat(awaitItem().first().isFavorite).isEqualTo(true)
            }
    }
}

private fun testTopicEntity(
    id: String,
    name: String,
    isFavorite: Boolean = false,
) = TopicEntity(
    id,
    name,
    "",
    0,
    "",
    isFavorite,
    "",
)

