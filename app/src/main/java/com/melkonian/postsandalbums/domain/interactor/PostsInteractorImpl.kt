package com.melkonian.postsandalbums.domain.interactor

import com.haroldadmin.cnradapter.NetworkResponse
import com.melkonian.postsandalbums.domain.entity.ErrorEntity
import com.melkonian.postsandalbums.domain.entity.PostEntity
import com.melkonian.postsandalbums.domain.repository.PostsRepo
import javax.inject.Inject

class PostsInteractorImpl @Inject constructor(
    private val repo: PostsRepo
) : PostsInteractor {
    override suspend fun getPosts(): NetworkResponse<List<PostEntity>, ErrorEntity> {
        return repo.getPosts()
    }
}