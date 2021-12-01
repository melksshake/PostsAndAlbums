package com.melkonian.postsandalbums.domain.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.melkonian.postsandalbums.domain.entity.ErrorEntity
import com.melkonian.postsandalbums.domain.entity.PostEntity
import com.melkonian.postsandalbums.domain.interactor.PostsInteractor

class PostsFakeInteractorImpl : PostsInteractor {
    var postsServiceData: LinkedHashMap<String, PostEntity> = LinkedHashMap()

    // for test purposes only
    fun addPosts(posts: List<PostEntity>) {
        posts.forEach { postsServiceData[it.id] = it }
    }

    override suspend fun getPosts(): NetworkResponse<List<PostEntity>, ErrorEntity> {
        return NetworkResponse.Success(
            postsServiceData.values.toList(),
            null,
            200
        )
    }
}