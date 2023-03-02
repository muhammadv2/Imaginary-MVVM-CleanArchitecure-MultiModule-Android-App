package com.developance.network

import com.developance.network.model.NetworkPhoto
import com.developance.network.model.NetworkTopic
import com.developance.network.model.Response

interface ImaginaryNetworkDataSource {

    suspend fun fetchTopics(page: Int?): List<NetworkTopic>
    suspend fun fetchTopicPhotos(slug: String?, page: Int?): List<NetworkPhoto>

    suspend fun fetchPhoto(id: String?): NetworkPhoto

    suspend fun searchPhotos(query: String?, page: Int?): Response

}