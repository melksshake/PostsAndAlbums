package com.melkonian.postsandalbums.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.melkonian.postsandalbums.domain.entity.PostEntity
import com.melkonian.postsandalbums.domain.repository.PostsFakeInteractorImpl
import com.melkonian.postsandalbums.presentation.mapper.PostsListMapper
import com.melkonian.postsandalbums.presentation.models.PostModel
import com.melkonian.postsandalbums.utils.Mapper
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PostsListViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var postsViewModel: PostsListViewModel
    private lateinit var postsInteractor: PostsFakeInteractorImpl
    private lateinit var postListMapper: Mapper<PostEntity, PostModel>

    @Before
    fun setUp() {
        postsInteractor = PostsFakeInteractorImpl()
        val posts = listOf(
            PostEntity(userId = "1", id = "1", title = "Title1", body = "Body1"),
            PostEntity(userId = "1", id = "2", title = "Title2", body = "Body2"),
            PostEntity(userId = "1", id = "3", title = "Title3", body = "Body3")
        )
        postsInteractor.addPosts(posts)
        postListMapper = PostsListMapper()
        postsViewModel = PostsListViewModel(postsInteractor, postListMapper)
    }

    @Test
    fun test_checkIf_allTasksReturns() {
        val tasksFromRepo = runBlocking { postsInteractor.getPosts() }
        assertThat(tasksFromRepo, is())
    }
}