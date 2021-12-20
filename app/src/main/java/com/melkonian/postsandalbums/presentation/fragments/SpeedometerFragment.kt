package com.melkonian.postsandalbums.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.viewModels
import com.melkonian.postsandalbums.R
import com.melkonian.postsandalbums.databinding.FmtSpeedometerBinding
import com.melkonian.postsandalbums.presentation.base.BaseFragment
import com.melkonian.postsandalbums.presentation.viewmodels.SpeedometerViewModel
import com.melkonian.postsandalbums.presentation.viewmodels.SpeedometerViewModel.Companion.START_POINTER_DEGREE
import com.melkonian.postsandalbums.utils.setupToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpeedometerFragment : BaseFragment() {
    private lateinit var binding: FmtSpeedometerBinding

    override val viewModel: SpeedometerViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FmtSpeedometerBinding.inflate(inflater, container, false)

        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        WindowInsetsControllerCompat(requireActivity().window, binding.speedometerViewContainer).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(binding.toolbar)

        binding.startResetSpeedometerBtn.setOnClickListener {
            viewModel.isAllowToReset.value?.let {
                if (it) {
                    viewModel.onResetClicked()
                } else {
                    viewModel.onStartClicked()
                }
            }
        }
        binding.stopSpeedometerBtn.setOnClickListener { viewModel.onStopClicked() }

        binding.startRandomlySwitch.setOnCheckedChangeListener { _, isEnabled -> viewModel.setIsRandomValues(isEnabled) }

        binding.speedometerView.setPointerLineRotateDegree(START_POINTER_DEGREE)

        viewModel.degree.observe(viewLifecycleOwner, { degree ->
            binding.speedometerView.setPointerLineRotateDegree(degree)
        })
        viewModel.isAllowToReset.observe(viewLifecycleOwner, {
            binding.startResetSpeedometerBtn.text = if (it) {
                getString(R.string.speedometer_reset_btn)
            } else {
                getString(R.string.speedometer_start_btn)
            }
        })
        viewModel.isInProgress.observe(viewLifecycleOwner, {
            binding.startResetSpeedometerBtn.isEnabled = !it
        })
    }
}