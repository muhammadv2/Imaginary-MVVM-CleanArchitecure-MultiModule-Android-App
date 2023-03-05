package com.developance.network.fake

import JvmUnitTestFakeAssetManager
import android.annotation.SuppressLint
import com.developance.common.Dispatcher
import com.developance.common.ImaginaryDispatchers
import com.developance.network.ImaginaryNetworkDataSource
import com.developance.network.model.NetworkPhoto
import com.developance.network.model.NetworkTopic
import com.developance.network.model.Response
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.reflect.Type
import java.util.*
import javax.inject.Inject


class FakeImaginaryNetworkDataSource @Inject constructor(
    @Dispatcher(ImaginaryDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val asset: FakeAssetManager = JvmUnitTestFakeAssetManager,
) : ImaginaryNetworkDataSource {

    private val gson: Gson = Gson()

     var failureMsg: String? = null


    companion object {
        private const val TOPIC_ASSET = "topics.json"
        private const val PHOTOS_ASSET = "photos.json"
        private const val PHOTO_ASSET = "photo.json"
        private const val PHOTO_SEARCH_ASSET = "search.json"
    }


    @SuppressLint("VisibleForTests")
    override suspend fun fetchTopics(page: Int?): List<NetworkTopic> =
        withContext(ioDispatcher) {
            failureMsg?.let { throw IOException() }
            val topicsJson = asset.open(TOPIC_ASSET)
            val listType: Type = object : TypeToken<List<NetworkTopic?>?>() {}.type
            gson.fromJson(topicsJson, listType)
        }

    override suspend fun fetchTopicPhotos(slug: String?, page: Int?): List<NetworkPhoto> =
        withContext(ioDispatcher) {
            failureMsg?.let { throw IOException() }
            val photosJson = asset.open(PHOTOS_ASSET)
            val listType: Type = object : TypeToken<List<NetworkPhoto?>?>() {}.type
            gson.fromJson(photosJson, listType)
        }

    override suspend fun fetchPhotoDetails(id: String?): NetworkPhoto =
        withContext(ioDispatcher) {
            val photoJson = asset.open(PHOTO_ASSET)
            gson.fromJson(photoJson, NetworkPhoto::class.java)
        }

    override suspend fun fetchPhotosByQuery(query: String?, page: Int?): Response =
        withContext(ioDispatcher) {
            failureMsg?.let { throw IOException() }
            val photoJson = asset.open(PHOTO_SEARCH_ASSET)
            gson.fromJson(photoJson, Response::class.java)
        }
}


