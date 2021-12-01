package com.melkonian.postsandalbums.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.melkonian.postsandalbums.com.melkonian.postsandalbums.utils.HorizontalDividerItemDecoration
import com.melkonian.postsandalbums.databinding.FmtPostsListBinding
import com.melkonian.postsandalbums.presentation.adapters.PostsAdapter
import com.melkonian.postsandalbums.presentation.base.BaseFragment
import com.melkonian.postsandalbums.presentation.models.PostModel
import com.melkonian.postsandalbums.presentation.viewmodels.PostsListViewModel
import com.melkonian.postsandalbums.presentation.viewmodels.PostsListViewModel.PostsListUiState
import com.melkonian.postsandalbums.utils.PostsListItemTouchHelper
import com.melkonian.postsandalbums.utils.setupToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsListFragment : BaseFragment() {
    override val viewModel: PostsListViewModel by viewModels()

    private lateinit var binding: FmtPostsListBinding

    private val postsAdapter = PostsAdapter()

    private val itemTouchHelper by lazy { ItemTouchHelper(PostsListItemTouchHelper(postsAdapter)) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FmtPostsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(binding.toolbar)

        binding.errorLoading.errorRefreshButton.setOnClickListener { viewModel.onReloadClicked() }

        viewModel.postsListUiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                PostsListUiState.Loading -> showLoading()
                PostsListUiState.Error -> showError()
                is PostsListUiState.Data -> showData(state.posts)
            }
        }
    }

    private fun showLoading() {
        binding.run {
            loadingView.root.visibility = View.VISIBLE
            errorLoading.root.visibility = View.GONE
            postsList.visibility = View.GONE
        }
    }

    private fun showError() {
        binding.run {
            loadingView.root.visibility = View.GONE
            errorLoading.root.visibility = View.VISIBLE
            postsList.visibility = View.GONE
        }
    }

    private fun showData(data: List<PostModel>) {
        binding.run {
            loadingView.root.visibility = View.GONE
            errorLoading.root.visibility = View.GONE
            postsList.visibility = View.VISIBLE

            itemTouchHelper.attachToRecyclerView(postsList)
            postsList.run {
                adapter = postsAdapter.apply {
                    hasFixedSize()
                    submitList(data)
                    setOnItemClickAction { post -> viewModel.onListItemClicked(post) }
                }
                addItemDecoration(HorizontalDividerItemDecoration(requireContext()))
            }
        }
    }
}