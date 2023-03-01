package com.developance.network

import com.developance.network.model.RemotePhoto
import com.developance.network.model.RemoteTopic
import com.developance.network.model.Response

interface ImaginaryNetworkDataSource {

    suspend fun fetchTopics(page: Int?): List<RemoteTopic>
    suspend fun fetchTopicPhotos(slug: String?, page: Int?): List<RemotePhoto>

    suspend fun fetchPhoto(id: String?): RemotePhoto

    suspend fun searchPhotos(query: String?, page: Int?): Response

}