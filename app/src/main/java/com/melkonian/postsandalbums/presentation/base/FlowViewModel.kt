package com.melkonian.postsandalbums.presentation.base

abstract class FlowViewModel<T : SharedDataViewModel> : BaseViewModel() {

    private lateinit var _sharedDataViewModel: T
    val sharedDataViewModel: T
        get() {
            if (!isInitialized()) {
                throw RuntimeException(
                    "${javaClass.simpleName} is not fully initialized yet. Consider using `lazy` initialization, " +
                            "or `get() = ...` for fields that reference `sharedDataViewModel`!"
                )
            }
            return _sharedDataViewModel
        }

    fun setSharedViewModel(vm: T) {
        check(!isInitialized())
        _sharedDataViewModel = vm
    }

    fun isInitialized() = ::_sharedDataViewModel.isInitialized
}