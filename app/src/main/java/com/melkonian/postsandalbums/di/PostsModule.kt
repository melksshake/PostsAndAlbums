package com.melkonian.postsandalbums.di

import com.melkonian.postsandalbums.data.repository.PostsRepoImpl
import com.melkonian.postsandalbums.data.service.PostsService
import com.melkonian.postsandalbums.data.source.remoteapi.PostsApi
import com.melkonian.postsandalbums.data.source.remoteapi.PostsMockApi
import com.melkonian.postsandalbums.domain.entity.PostEntity
import com.melkonian.postsandalbums.domain.interactor.PostsInteractor
import com.melkonian.postsandalbums.domain.interactor.PostsInteractorImpl
import com.melkonian.postsandalbums.domain.repository.PostsRepo
import com.melkonian.postsandalbums.presentation.mapper.PostsListMapper
import com.melkonian.postsandalbums.presentation.models.PostModel
import com.melkonian.postsandalbums.utils.Mapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class PostsModule {
    @Binds
    @ApiService
    @ExperimentalCoroutinesApi
    abstract fun bindPostsApi(impl: PostsService): PostsApi

    @Binds
    @MockApi
    abstract fun bindPostsMockApi(impl: PostsMockApi): PostsApi

    @Binds
    abstract fun bindPostsRepo(impl: PostsRepoImpl): PostsRepo

    @Binds
    abstract fun bindPostsInteractor(impl: PostsInteractorImpl): PostsInteractor

    @Binds
    abstract fun bindPostsListMapper(impl: PostsListMapper): Mapper<PostEntity, PostModel>
}