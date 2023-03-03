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
import java.lang.reflect.Type
import java.util.*
import javax.inject.Inject

class FakeImaginaryNetworkDataSource @Inject constructor(
    @Dispatcher(ImaginaryDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val gson: Gson = Gson(),
    private val asset: FakeAssetManager = JvmUnitTestFakeAssetManager
) : ImaginaryNetworkDataSource {

    companion object {
        private const val TOPIC_ASSET = "topics.json"
        private const val PHOTOS_ASSET = "photos.json"
        private const val PHOTO_ASSET = "photo.json"
        private const val PHOTO_SEARCH_ASSET = "search.json"
    }


    @SuppressLint("VisibleForTests")
    override suspend fun fetchTopics(page: Int?): List<NetworkTopic> =
        withContext(ioDispatcher) {
            val topicsJson = asset.open(TOPIC_ASSET).bufferedReader()
            val listType: Type = object : TypeToken<List<NetworkTopic?>?>() {}.type
            gson.fromJson(topicsJson, listType)
        }

    override suspend fun fetchTopicPhotos(slug: String?, page: Int?): List<NetworkPhoto> =
        withContext(ioDispatcher) {
            val photosJson = asset.open(PHOTOS_ASSET).bufferedReader()
            val listType: Type = object : TypeToken<List<NetworkPhoto?>?>() {}.type
            gson.fromJson(photosJson, listType)
        }

    override suspend fun fetchPhoto(id: String?): NetworkPhoto =
        withContext(ioDispatcher) {
            val photoJson = asset.open(PHOTO_ASSET).bufferedReader()
            gson.fromJson(photoJson, NetworkPhoto::class.java)
        }

    override suspend fun searchPhotos(query: String?, page: Int?): Response =
        withContext(ioDispatcher) {
            val photoJson = asset.open(PHOTO_SEARCH_ASSET).bufferedReader()
            gson.fromJson(photoJson, Response::class.java)
        }
}