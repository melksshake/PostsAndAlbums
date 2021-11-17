package com.melkonian.postsandalbums.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.melkonian.postsandalbums.databinding.FmtPostDetailedBinding
import com.melkonian.postsandalbums.presentation.base.BaseFragment
import com.melkonian.postsandalbums.presentation.viewmodels.PostDetailedViewModel
import com.melkonian.postsandalbums.utils.setupToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailedFragment : BaseFragment() {
    override val viewModel: PostDetailedViewModel by viewModels()

    private lateinit var binding: FmtPostDetailedBinding

    private val args: PostDetailedFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setArgs(args)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FmtPostDetailedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(binding.toolbar)

        viewModel.postData.observe(viewLifecycleOwner, { post ->
            binding.run {
                postIdTextView.text = post.id
                postTitleTextView.text = post.title
                postBodyTextView.text = post.body
            }
        })
    }
}