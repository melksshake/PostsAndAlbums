package com.melkonian.postsandalbums.domain.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.melkonian.postsandalbums.domain.entity.ErrorEntity
import com.melkonian.postsandalbums.domain.entity.PostEntity

interface PostsRepo {
    suspend fun getPosts(): NetworkResponse<List<PostEntity>, ErrorEntity>
}