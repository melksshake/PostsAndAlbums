package com.melkonian.postsandalbums.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.melkonian.postsandalbums.domain.entity.PostEntity
import com.melkonian.postsandalbums.domain.interactor.PostsInteractor
import com.melkonian.postsandalbums.presentation.base.BaseViewModel
import com.melkonian.postsandalbums.presentation.fragments.PostsListFragmentDirections
import com.melkonian.postsandalbums.presentation.models.PostModel
import com.melkonian.postsandalbums.utils.Mapper
import com.melkonian.postsandalbums.utils.updateValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsListViewModel @Inject constructor(
    private val interactor: PostsInteractor,
    private val postListMapper: Mapper<PostEntity, PostModel>
) : BaseViewModel() {

    private val _isLoading: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    private val _isError: MutableLiveData<Boolean> by lazy { MutableLiveData() }

    private val _postsList: MutableLiveData<List<PostModel>> by lazy {
        viewModelScope.launch { loadPosts() }
        MutableLiveData<List<PostModel>>()
    }

    val postsList1: StateFlow<List<PostModel>> = interactor.getPostsFlow()
        .map { list ->
            mutableListOf<PostModel>().apply {
                list.forEach {
                    postListMapper.map(it)
                }
            }
        }.stateIn(
            initialValue = emptyList(),
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000)
        )

    val postsListUiState: LiveData<PostsListUiState> by lazy {
        MediatorLiveData<PostsListUiState>().apply {
            addSource(_isLoading) {
                compileUiState(
                    it,
                    _isError.value,
                    _postsList.value
                ).let(::updateValue)
            }

            addSource(_isError) {
                compileUiState(
                    _isLoading.value,
                    it,
                    _postsList.value
                ).let(::updateValue)
            }

            addSource(_postsList) {
                compileUiState(
                    _isLoading.value,
                    _isError.value,
                    it
                ).let(::updateValue)
            }
        }
    }

    private fun compileUiState(
        isLoading: Boolean?,
        isError: Boolean?,
        posts: List<PostModel>?
    ): PostsListUiState {
        return when {
            isLoading == true -> PostsListUiState.Loading
            isError == true -> PostsListUiState.Error
            posts != null -> PostsListUiState.Data(posts)
            else -> PostsListUiState.Error
        }
    }

    private suspend fun loadPosts() {
        _isLoading.value = true
        _isError.value = false

        interactor.getPosts()
            .handleResponse(
                onAnyError = {
                    _isLoading.value = false
                    _isError.value = true
                }
            ) { postsList ->
                val postListModels = mutableListOf<PostModel>().apply {
                    postsList.forEach { add(postListMapper.map(it)) }
                }

                _postsList.postValue(postListModels)

                _isLoading.value = false
                _isError.value = false
            }
    }

    fun onReloadClicked() {
        viewModelScope.launch { loadPosts() }
    }

    fun onListItemClicked(post: PostModel) {
        navigate(PostsListFragmentDirections.actionPostsListToPostDetailed(post))
    }

    sealed class PostsListUiState {
        object Loading : PostsListUiState()
        object Error : PostsListUiState()
        data class Data(val posts: List<PostModel>) : PostsListUiState()
    }
}