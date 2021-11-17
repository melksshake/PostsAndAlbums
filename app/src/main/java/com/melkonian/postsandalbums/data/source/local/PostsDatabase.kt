package com.melkonian.postsandalbums.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.melkonian.postsandalbums.domain.entity.PostEntity
import com.melkonian.postsandalbums.data.source.local.PostsDao

@Database(entities = [PostEntity::class], version = 1, exportSchema = false)
abstract class PostsDatabase : RoomDatabase() {
    abstract fun postsDao(): PostsDao
}