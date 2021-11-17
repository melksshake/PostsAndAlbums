package com.melkonian.postsandalbums.di

import android.content.Context
import androidx.room.Room
import com.melkonian.postsandalbums.data.source.local.PostsDatabase
import com.melkonian.postsandalbums.data.source.local.PostsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): PostsDatabase {
        return Room.databaseBuilder(
            appContext,
            PostsDatabase::class.java,
            "Posts.db"
        ).build()
    }

    @Provides
    fun provideTasksDao(albumsDatabase: PostsDatabase): PostsDao {
        return albumsDatabase.postsDao()
    }
}