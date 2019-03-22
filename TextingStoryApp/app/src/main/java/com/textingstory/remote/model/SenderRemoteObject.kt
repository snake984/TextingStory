package com.textingstory.remote.model

import com.google.gson.annotations.SerializedName

data class Sender(@SerializedName("name") val name: String)

fun Sender.map() = com.textingstory.domain.model.Sender(name)
