package com.melkonian.postsandalbums.presentation.base

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.melkonian.postsandalbums.utils.setupNavigation

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {
    abstract val viewModel: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigation(viewModel)
    }
}