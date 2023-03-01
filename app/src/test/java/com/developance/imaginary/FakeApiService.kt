package com.developance.imaginary

import com.developance.imaginary.topic.data.remote.RemoteTopic
import com.developance.imaginary.topic.data.remote.TopicsApiService
import kotlinx.coroutines.flow.Flow

class FakeApiService(private val errorMessage : String? = null) : TopicsApiService {


    override suspend fun fetchInterestingTopics(): Flow<List<RemoteTopic>> {
        TODO("Not yet implemented")
    }
}