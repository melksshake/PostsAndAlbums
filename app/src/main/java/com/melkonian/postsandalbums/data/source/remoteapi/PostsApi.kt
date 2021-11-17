package com.melkonian.postsandalbums.data.source.remoteapi

import com.haroldadmin.cnradapter.NetworkResponse
import com.melkonian.postsandalbums.domain.entity.ErrorEntity
import com.melkonian.postsandalbums.domain.entity.PostEntity
import retrofit2.http.GET

interface PostsApi {
    @GET("/posts")
    suspend fun getPosts(): NetworkResponse<List<PostEntity>, ErrorEntity>
}