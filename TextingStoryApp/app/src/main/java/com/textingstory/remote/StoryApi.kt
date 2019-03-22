package com.textingstory.remote

import com.textingstory.remote.model.StoryRemoteObject
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface StoryApi {
    @GET("/story/{uid}/")
    fun fetchStory(@Path("uid") uid: String): Deferred<StoryRemoteObject>
}