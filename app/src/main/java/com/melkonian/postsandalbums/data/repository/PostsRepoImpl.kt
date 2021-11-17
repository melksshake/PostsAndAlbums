package com.melkonian.postsandalbums.data.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.melkonian.postsandalbums.data.source.remoteapi.PostsApi
import com.melkonian.postsandalbums.di.ApiService
import com.melkonian.postsandalbums.domain.entity.ErrorEntity
import com.melkonian.postsandalbums.domain.entity.PostEntity
import com.melkonian.postsandalbums.domain.repository.PostsRepo
import javax.inject.Inject

class PostsRepoImpl @Inject constructor(
    @ApiService private val api: PostsApi
) : PostsRepo {
    override suspend fun getPosts(): NetworkResponse<List<PostEntity>, ErrorEntity> {
        return api.getPosts()
    }
}