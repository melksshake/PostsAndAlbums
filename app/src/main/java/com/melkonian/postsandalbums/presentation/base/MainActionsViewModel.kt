package com.melkonian.postsandalbums.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.melkonian.postsandalbums.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainActionsViewModel @Inject constructor() : ViewModel() {
    private val _actionResetToSignUp: SingleLiveEvent<Unit> by lazy {
        SingleLiveEvent()
    }
    val actionResetToSignUp: LiveData<Unit> = _actionResetToSignUp

    private val _actionResetToAuth: SingleLiveEvent<Unit> by lazy {
        SingleLiveEvent()
    }
    val actionResetToAuth: LiveData<Unit> = _actionResetToAuth

    fun signOut() {
        Timber.d("signOut")
        _actionResetToSignUp.value = Unit
    }

    fun reAuth() {
        Timber.d("reAuth")
        _actionResetToAuth.value = Unit
    }
}