package com.melkonian.postsandalbums.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.melkonian.postsandalbums.databinding.ItemPostBinding
import com.melkonian.postsandalbums.presentation.adapters.PostsAdapter.ViewHolder
import com.melkonian.postsandalbums.presentation.models.PostModel

class PostsAdapter : ListAdapter<PostModel, ViewHolder>(DIFF_CALLBACK), MoveItem {
    companion object {
        /**
         * Callback for calculating the diff between two non-null items in a list.
         *
         * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
         * list that's been passed to `submitList`.
         */
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PostModel>() {
            override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var onItemClick: ((PostModel) -> Unit)? = null
    private var onItemLongClick: ((PostModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ).apply {
            binding.root.setOnClickListener { onItemClick?.invoke(getItem(bindingAdapterPosition)) }
            binding.root.setOnLongClickListener {
                onItemLongClick?.invoke(getItem(bindingAdapterPosition))
                return@setOnLongClickListener true
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setOnItemClickAction(action: ((PostModel) -> Unit)?) {
        onItemClick = action
    }

    fun setOnItemLongClickAction(action: ((PostModel) -> Unit)?) {
        onItemLongClick = action
    }

    override fun moveItem(from: Int, to: Int) {
        val list = currentList.toMutableList()
        val fromLocation = list[from]
        list.removeAt(from)

        if (to < from) {
            list.add(to + 1 , fromLocation)
        } else {
            list.add(to - 1, fromLocation)
        }

        submitList(list)
    }

    class ViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostModel) {
            binding.postTitleTextView.text = post.title
            binding.postBodyTextView.text = post.body
        }
    }
}
