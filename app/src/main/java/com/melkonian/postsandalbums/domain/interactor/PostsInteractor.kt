package com.melkonian.postsandalbums.domain.interactor

import com.haroldadmin.cnradapter.NetworkResponse
import com.melkonian.postsandalbums.domain.entity.ErrorEntity
import com.melkonian.postsandalbums.domain.entity.PostEntity
import kotlinx.coroutines.flow.Flow

interface PostsInteractor {
    suspend fun getPosts(): NetworkResponse<List<PostEntity>, ErrorEntity>
    fun getPostsFlow(): Flow<List<PostEntity>>
}