package com.melkonian.postsandalbums.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.melkonian.postsandalbums.databinding.FmtStartBinding
import com.melkonian.postsandalbums.presentation.base.BaseFragment
import com.melkonian.postsandalbums.presentation.viewmodels.StartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment : BaseFragment() {
    private lateinit var binding: FmtStartBinding

    override val viewModel: StartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FmtStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            openQrGenerationBtn.setOnClickListener { viewModel.onOpenQrClicked() }
            openPostsListBtn.setOnClickListener { viewModel.onOpenPostsListClicked() }
        }
    }
}