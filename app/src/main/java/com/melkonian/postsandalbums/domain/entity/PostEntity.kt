package com.melkonian.postsandalbums.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "Posts")
data class PostEntity @JvmOverloads constructor(
    @PrimaryKey @ColumnInfo(name = "entryId") var entryId: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "userId") val userId: String = "",
    @ColumnInfo(name = "id") val id: String = "",
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "body") val body: String = ""
)