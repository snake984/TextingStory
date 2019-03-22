package com.textingstory.domain.usecases

import com.textingstory.domain.model.Story
import com.textingstory.repositories.StoriesRepositoryImpl

class FetchStory {
    private val storiesRepository = StoriesRepositoryImpl()

    suspend fun launch(uid: String): Result {
        return try {
            Result.Success(storiesRepository.fetchStory(uid))
        } catch (exception: Exception) {
            exception.printStackTrace()
            Result.Error(exception)
        }
    }

    sealed class Result {
        class Success(val data: Story) : Result()
        class Error(val exception: Throwable) : Result()
    }
}