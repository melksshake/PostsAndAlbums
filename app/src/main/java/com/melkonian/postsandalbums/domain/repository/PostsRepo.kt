package com.melkonian.postsandalbums.domain.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.melkonian.postsandalbums.domain.entity.ErrorEntity
import com.melkonian.postsandalbums.domain.entity.PostEntity
import kotlinx.coroutines.flow.Flow

interface PostsRepo {
    suspend fun getPosts(): NetworkResponse<List<PostEntity>, ErrorEntity>
    fun getPostsFlow(): Flow<List<PostEntity>>
}