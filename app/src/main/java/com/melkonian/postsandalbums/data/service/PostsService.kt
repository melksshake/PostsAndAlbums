package com.melkonian.postsandalbums.data.service

import com.haroldadmin.cnradapter.NetworkResponse
import com.melkonian.postsandalbums.BuildConfig
import com.melkonian.postsandalbums.data.source.remoteapi.PostsApi
import com.melkonian.postsandalbums.di.MockApi
import com.melkonian.postsandalbums.domain.MockManager
import com.melkonian.postsandalbums.domain.entity.ErrorEntity
import com.melkonian.postsandalbums.domain.entity.PostEntity
import dagger.Lazy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import javax.inject.Inject

@ExperimentalCoroutinesApi
class PostsService @Inject constructor(
    private val retrofit: Retrofit,
    private val mockManager: MockManager
) : PostsApi {
    @Inject
    @MockApi
    lateinit var mockApi: Lazy<PostsApi>

    private val retrofitApi by lazy { retrofit.create(PostsApi::class.java) }

    private val api: PostsApi
        get() = if (BuildConfig.DEBUG && mockManager.isMockEnabled) {
            mockApi.get()
        } else {
            retrofitApi
        }

    override suspend fun getPosts(): NetworkResponse<List<PostEntity>, ErrorEntity> {
        return api.getPosts()
    }

    override fun getPostsFlow(): Flow<List<PostEntity>> {
        return api.getPostsFlow()
    }
}