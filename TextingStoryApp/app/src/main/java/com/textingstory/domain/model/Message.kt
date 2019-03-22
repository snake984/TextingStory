package com.textingstory.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Message(
    val ordinal: Int,
    val text: String,
    val sender: Sender
) : Parcelable