package com.textingstory.domain.model


data class Story(
    val title: String,
    val description: String,
    val messages: List<Message>,
    val coverImageUrl: String
)