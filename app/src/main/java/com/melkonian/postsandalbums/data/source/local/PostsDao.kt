package com.melkonian.postsandalbums.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.melkonian.postsandalbums.domain.entity.PostEntity

@Dao
interface PostsDao {
    @Query("SELECT * FROM Posts ORDER BY title")
    fun getAllPosts(): LiveData<List<PostEntity>>

    @Insert
    fun insertAllPosts(users: List<PostEntity>)
}