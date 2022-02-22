package com.melkonian.postsandalbums.presentation.base

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.melkonian.postsandalbums.R
import com.melkonian.postsandalbums.utils.setupNavigation
import java.lang.ref.WeakReference

abstract class BaseFragment : Fragment() {
    companion object {
        private val otherStaticModels = mutableMapOf<BaseViewModel, WeakReference<BaseFragment>>()
    }

    abstract val viewModel: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerViewModel(viewModel)

//        vm.errorMessage().observe(viewLifecycleOwner, { msgId ->
//            view?.let { showErrorSnackBar(it, msgId) }
//        })
//
//        vm.errorStringMessage().observe(viewLifecycleOwner, { messageText ->
//            view?.let { showErrorSnackBar(it, messageText) }
//        })
//
        viewModel.message().observe(viewLifecycleOwner) { msgId ->
            showMessageSnackBar(msgId)
        }
    }

    private fun showMessageSnackBar(@StringRes msgId: Int) {
        Snackbar.make(requireActivity().findViewById(android.R.id.content), msgId, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(requireContext().getColor(R.color.colorBackground))
            .setTextColor(Color.WHITE)
            .show()
    }

    private fun registerViewModel(vm: BaseViewModel, addToStatic: Boolean = false) {
        setupNavigation(vm)

        if (addToStatic) {
            otherStaticModels[vm] = WeakReference(this)
        }
    }
}