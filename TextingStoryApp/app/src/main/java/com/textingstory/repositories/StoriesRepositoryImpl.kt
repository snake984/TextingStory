package com.textingstory.repositories

import com.textingstory.domain.model.Story
import com.textingstory.remote.ApiClient
import com.textingstory.remote.model.map

class StoriesRepositoryImpl : StoriesRepository {
    private val apiClient = ApiClient()

    override suspend fun fetchStory(uid: String): Story =
        apiClient.fetchStory(uid)
            .await()
            .map()
}