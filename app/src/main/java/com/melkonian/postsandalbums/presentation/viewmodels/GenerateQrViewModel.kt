package com.melkonian.postsandalbums.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.melkonian.postsandalbums.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GenerateQrViewModel @Inject constructor() : BaseViewModel() {
    private val _phaseToQr: MutableLiveData<String> by lazy { MutableLiveData() }
    val phaseToQr: LiveData<String>
        get() = _phaseToQr

    fun setPhraseToQr(value: String) {
        _phaseToQr.postValue(value)
    }
}