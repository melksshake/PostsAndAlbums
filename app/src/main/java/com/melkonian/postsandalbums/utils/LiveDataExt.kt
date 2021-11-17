package com.melkonian.postsandalbums.utils

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.updateValue(newValue: T) {
    if (value != newValue) value = newValue
}

fun <T> MutableLiveData<T>.postUpdateValue(newValue: T) {
    if (value != newValue) postValue(newValue)
}