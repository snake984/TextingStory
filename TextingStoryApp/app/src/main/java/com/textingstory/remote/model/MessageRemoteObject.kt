package com.textingstory.remote.model

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("ordinalInStory") val ordinal: Int,
    @SerializedName("text") val text: String,
    @SerializedName("sender") val sender: Sender
)

fun Message.map() = com.textingstory.domain.model.Message(
    ordinal = ordinal,
    text = text,
    sender = sender.map()
)
