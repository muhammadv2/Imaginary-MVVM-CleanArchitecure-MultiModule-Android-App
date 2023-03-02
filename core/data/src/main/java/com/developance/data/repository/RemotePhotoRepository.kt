package com.developance.data.repository

import com.developance.data.paging.PhotosPagingSource
import com.developance.data.paging.SearchPagingSource
import com.developance.data_store.PhotosStoreManager
import com.developance.database.TopicsDatabase
import com.developance.database.model.asExternalModel
import com.developance.model.data.UserPhoto
import com.developance.model.data.defaultRandomTopic
import com.developance.network.ImaginaryNetworkDataSource
import com.developance.network.model.asDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemotePhotoRepository @Inject constructor(
    topicsDb: TopicsDatabase,
    private val imaginaryApi: ImaginaryNetworkDataSource,
    private val dataStoreManager: PhotosStoreManager,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PhotosRepository {


    override val favoritePhotosIdsStream = dataStoreManager.bookmarkedPhotosIds

    override val favoritedTopicsStream = topicsDb.topicDao.favoritedTopicsStream()
        .map { topics ->
            topics.map { topic ->
                topic.asExternalModel()
            }
        }.onEmpty {
            emit(listOf(defaultRandomTopic))
        }

    override fun getPhotoPagingSource(topicsSlug: String) =
        PhotosPagingSource(imaginaryApi, topicsSlug)

    override fun getSearchPagingSource(query: String) =
        SearchPagingSource(imaginaryApi, query)

    override suspend fun toggleFavoritePhoto(id: String, isBookmarked: Boolean) {

        if (isBookmarked)
            dataStoreManager.addBookmarkedPhotoId(id)
        else
            dataStoreManager.removeBookmarkedPhotoId(id)
    }

    override fun fetchPhoto(id: String): Flow<UserPhoto> = flow {
        emit(imaginaryApi.fetchPhoto(id).asDomain())
    }

    override suspend fun fetchFavoritePhotos(ids: List<String>): List<UserPhoto> =
        withContext(defaultDispatcher) {
            val userPhotoDetails = mutableListOf<UserPhoto>()
            ids.forEach {
                launch {
                    try {
                        userPhotoDetails.add(imaginaryApi.fetchPhoto(it).asDomain())
                    } catch (ex: Exception) {
                        //no need to handle exception as if anything wrong happend when one photo
                        // data will not be added to the list
                        ex.printStackTrace()
                    }
                }
            }
            userPhotoDetails.toList()
        }

}

