package com.melkonian.postsandalbums.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostModel(
    val userId: String,
    val id: String,
    val title: String,
    val body: String
) : Parcelable