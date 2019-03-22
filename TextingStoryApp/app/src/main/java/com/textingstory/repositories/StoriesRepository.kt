package com.textingstory.repositories

import com.textingstory.domain.model.Story

interface StoriesRepository {
    suspend fun fetchStory(uid: String) : Story
}