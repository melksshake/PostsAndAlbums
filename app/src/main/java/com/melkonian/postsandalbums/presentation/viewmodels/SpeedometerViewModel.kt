package com.melkonian.postsandalbums.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.melkonian.postsandalbums.presentation.base.BaseViewModel
import com.melkonian.postsandalbums.utils.updateValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class SpeedometerViewModel @Inject constructor() : BaseViewModel() {
    companion object {
        const val START_POINTER_DEGREE = -225f
        const val END_POINTER_DEGREE = 45f

        const val INCREASE_DELAY = 70L
        const val INCREASE_CONSEQUENTIALLY_DELAY = 70L
        const val RESET_DELAY = 5L
    }

    private val _isRandomValues: MutableLiveData<Boolean> by lazy { MutableLiveData(false) }
    val isRandomValues: LiveData<Boolean>
        get() = _isRandomValues

    private val _isInProgress: MutableLiveData<Boolean> by lazy { MutableLiveData(false) }
    val isInProgress: LiveData<Boolean>
        get() = _isInProgress

    private val _isAllowToReset: MutableLiveData<Boolean> by lazy { MutableLiveData(false) }
    val isAllowToReset: LiveData<Boolean>
        get() = _isAllowToReset

    private val _isStopClicked: MutableLiveData<Boolean> by lazy { MutableLiveData(false) }

    private val _degree: MutableLiveData<Float> by lazy { MutableLiveData(START_POINTER_DEGREE) }
    val degree: LiveData<Float>
        get() = _degree

    fun setIsRandomValues(value: Boolean) {
        _isRandomValues.updateValue(value)
    }

    fun onStartClicked() {
        when (_isRandomValues.value) {
            true -> startRandom()
            false -> startConsequentially()
            else -> startConsequentially()
        }
    }

    private fun startRandom() {
        viewModelScope.launch {
            _isInProgress.value = true

            while (_isStopClicked.value != true) {
                var degree = Random
                    .nextInt(START_POINTER_DEGREE.toInt(), END_POINTER_DEGREE.toInt() - 1)
                    .toFloat()

                val nextDegree = Random
                    .nextInt(START_POINTER_DEGREE.toInt(), END_POINTER_DEGREE.toInt() - 1)
                    .toFloat()

                while (degree <= nextDegree && _isStopClicked.value != true) {
                    degree++
                    _degree.value = degree

                    delay(INCREASE_DELAY)

                    if (degree == END_POINTER_DEGREE) {
                        _isInProgress.value = false
                        _isAllowToReset.value = true
                    }
                }

                if (_isStopClicked.value != true) {
                    _isInProgress.value = false
                    _isAllowToReset.value = true
                }
            }
        }
    }

    private fun startConsequentially() {
        viewModelScope.launch {
            _isInProgress.value = true

            var degree = _degree.value ?: return@launch
            while (degree <= END_POINTER_DEGREE) {
                if (_isStopClicked.value == true) {
                    break
                }

                degree++
                _degree.value = degree

                delay(INCREASE_CONSEQUENTIALLY_DELAY)

                if (degree == END_POINTER_DEGREE) {
                    _isInProgress.value = false
                    _isAllowToReset.value = true
                }
            }
        }
    }

    fun onResetClicked() {
        reset()
    }

    private fun reset() {
        viewModelScope.launch {
            _isInProgress.updateValue(true)
            _isStopClicked.updateValue(true)

            var degree = degree.value ?: return@launch
            while (degree > START_POINTER_DEGREE) {
                degree--
                _degree.value = degree
                delay(RESET_DELAY)

                if (degree == START_POINTER_DEGREE) {
                    _isInProgress.value = false
                    _isAllowToReset.value = false
                }
            }
        }

        _isStopClicked.updateValue(false)
    }

    fun onStopClicked() {
        _isStopClicked.updateValue(true)
        _isInProgress.value = false
        _isAllowToReset.value = true
    }
}