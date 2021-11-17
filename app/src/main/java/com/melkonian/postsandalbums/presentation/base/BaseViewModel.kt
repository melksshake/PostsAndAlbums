package com.melkonian.postsandalbums.presentation.base

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.haroldadmin.cnradapter.NetworkResponse
import com.melkonian.postsandalbums.presentation.base.BaseViewModel.NavigationCommand.To
import com.melkonian.postsandalbums.presentation.base.BaseViewModel.NavigationCommand.Up
import com.melkonian.postsandalbums.utils.SingleLiveEvent
import timber.log.Timber
import java.io.IOException

abstract class BaseViewModel : ViewModel() {
    private val _navigationCommands: SingleLiveEvent<NavigationCommand> by lazy {
        SingleLiveEvent()
    }

    val navigationCommands: LiveData<NavigationCommand> = _navigationCommands

    protected fun navigate(directions: NavDirections, popUpInclusive: Boolean = false) {
        _navigationCommands.postValue(To(directions, popUpInclusive))
    }

    protected fun navigateUp() {
        _navigationCommands.postValue(Up)
    }

    private val _errorMessage: SingleLiveEvent<Int> by lazy { SingleLiveEvent() }
    private val _errorStringMessage: SingleLiveEvent<String> by lazy { SingleLiveEvent() }
    private val _message: SingleLiveEvent<Int> by lazy { SingleLiveEvent() }

    fun errorMessage(): LiveData<Int> = _errorMessage

    fun errorStringMessage(): LiveData<String> = _errorStringMessage

    fun message(): LiveData<Int> = _message

    protected fun showError(@StringRes msgResId: Int) {
        _errorMessage.postValue(msgResId)
    }

    protected fun showError(message: String) {
        _errorStringMessage.postValue(message)
    }

    protected fun showMessage(@StringRes msgResId: Int) {
        _message.postValue(msgResId)
    }

    protected fun showDefaultServerError() {
//        showError(R.string.msg_default_server_error)
    }

    protected fun showUnknownError() {
//        showError(R.string.msg_unknown_error)
    }

    sealed class NavigationCommand {
        data class To(val directions: NavDirections, val popUpInclusive: Boolean = false) : NavigationCommand()
        object Up : NavigationCommand()
    }

    protected fun <T : Any, U : Any> NetworkResponse<T, U>.handleResponse(
        onAnyError: (() -> Unit) = {},
        onHandleError: ((U) -> Boolean) = { false },
        onServerError: ServerErrorHandler = DefaultServerErrorHandler(),
        onNetworkError: NetworkErrorHandler = DefaultNetworkErrorHandler(),
        onUnknownError: UnknownErrorHandler = DefaultUnknownErrorHandler(),
        onSuccess: ((T) -> Unit) = {}
    ): T? {
        when (this) {
            is NetworkResponse.Success -> {
                onSuccess.invoke(body)
            }
            is NetworkResponse.ServerError -> {
                if (body?.let(onHandleError) != true) {
                    onServerError.onServerError(code)
                }
            }
            is NetworkResponse.NetworkError -> {
                onNetworkError.onNetworkError(error)
            }
            is NetworkResponse.UnknownError -> {
                onUnknownError.onUnknownError(error)
            }
        }
        if (this !is NetworkResponse.Success) {
            onAnyError()
        }
        return if (this is NetworkResponse.Success) body else null
    }

    protected interface ServerErrorHandler {
        fun onServerError(code: Int)
    }

    protected open inner class DefaultServerErrorHandler : ServerErrorHandler {
        override fun onServerError(code: Int) {
            Timber.w("onServerError - $code")
            showDefaultServerError()
        }
    }

    protected interface NetworkErrorHandler {
        fun onNetworkError(error: IOException)
    }

    protected open inner class DefaultNetworkErrorHandler : NetworkErrorHandler {
        override fun onNetworkError(error: IOException) {
            Timber.w("onNetworkError - $error")
//            _errorMessage.postValue(R.string.msg_network_error)
        }
    }

    protected interface UnknownErrorHandler {
        fun onUnknownError(error: Throwable)
    }

    protected open inner class DefaultUnknownErrorHandler : UnknownErrorHandler {
        override fun onUnknownError(error: Throwable) {
            Timber.d("onUnknownError - $error")
            showUnknownError()
        }
    }
}