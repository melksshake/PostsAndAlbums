package com.melkonian.postsandalbums.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.melkonian.postsandalbums.presentation.adapters.PostsAdapter

class PostsListItemTouchHelper(private val adapter: PostsAdapter) :
    ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP
                or ItemTouchHelper.DOWN
                or ItemTouchHelper.START
                or ItemTouchHelper.END, 0
    ) {
    companion object {
        private const val ITEM_SELECTED_ALPHA = 0.5f
        private const val ITEM_RELEASED_ALPHA = 1.0f
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val from = viewHolder.bindingAdapterPosition
        val to = target.bindingAdapterPosition

        adapter.moveItem(from, to)
        adapter.notifyItemMoved(from, to)

        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)

        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            viewHolder?.itemView?.alpha = ITEM_SELECTED_ALPHA
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.alpha = ITEM_RELEASED_ALPHA
    }
}