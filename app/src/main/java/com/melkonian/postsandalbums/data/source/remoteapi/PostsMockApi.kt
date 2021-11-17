package com.melkonian.postsandalbums.data.source.remoteapi

import android.content.Context
import com.google.gson.Gson
import com.haroldadmin.cnradapter.NetworkResponse
import com.melkonian.postsandalbums.domain.entity.ErrorEntity
import com.melkonian.postsandalbums.domain.entity.PostEntity
import com.melkonian.postsandalbums.utils.fromAssets
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

class PostsMockApi @Inject constructor(
    @ApplicationContext val context: Context,
    private val gson: Gson
) : PostsApi {
    override suspend fun getPosts(): NetworkResponse<List<PostEntity>, ErrorEntity> {
        delay(Random.nextLong(300, 1000))
        return NetworkResponse.Success(
            gson.fromAssets(context, "posts.json"),
            null,
            200
        )
    }
}