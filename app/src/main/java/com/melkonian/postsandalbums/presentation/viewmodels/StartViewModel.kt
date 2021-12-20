package com.melkonian.postsandalbums.presentation.viewmodels

import com.melkonian.postsandalbums.presentation.base.BaseViewModel
import com.melkonian.postsandalbums.presentation.fragments.StartFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor() : BaseViewModel() {
    fun onOpenQrClicked() {
        navigate(StartFragmentDirections.actionStartScreenToGenerateQr())
    }

    fun onOpenPostsListClicked() {
        navigate(StartFragmentDirections.actionStartScreenToPostsList())
    }

    fun onOpenSpeedometerClicked() {
        navigate(StartFragmentDirections.actionStartScreenToSpeedometer())
    }
}