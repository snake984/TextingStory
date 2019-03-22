package com.textingstory.remote.model

import com.google.gson.annotations.SerializedName
import com.textingstory.domain.model.Story

data class StoryRemoteObject(
    @SerializedName("seriesTitle") val title: String,
    @SerializedName("storyDescription") val description: String,
    @SerializedName("messages") val messages: List<Message>,
    @SerializedName("coverImageFile") val coverImageFileRemoteObject: CoverImageFileRemoteObject
)

fun StoryRemoteObject.map() = Story(
    title = title,
    description = description,
    messages = messages.map { it.map() },
    coverImageUrl = coverImageFileRemoteObject.url
)
