package com.melkonian.postsandalbums.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.melkonian.postsandalbums.presentation.base.BaseViewModel
import com.melkonian.postsandalbums.presentation.fragments.PostDetailedFragmentArgs
import com.melkonian.postsandalbums.presentation.models.PostModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostDetailedViewModel @Inject constructor() : BaseViewModel() {

    private val _postData: MutableLiveData<PostModel> by lazy { MutableLiveData() }
    val postData: LiveData<PostModel>
        get() = _postData

    fun setArgs(value: PostDetailedFragmentArgs) {
        _postData.postValue(value.postModel)
    }
}