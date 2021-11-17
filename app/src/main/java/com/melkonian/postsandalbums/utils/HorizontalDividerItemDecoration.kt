package com.melkonian.postsandalbums.com.melkonian.postsandalbums.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.melkonian.postsandalbums.R
import kotlin.math.roundToInt

class HorizontalDividerItemDecoration(
    context: Context
) : ItemDecoration() {

    private val mDivider = ContextCompat.getDrawable(context, R.drawable.bg_list_item_divider)
    private val mBounds = Rect()

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.layoutManager == null || mDivider == null) {
            return
        }
        c.save()
        val left: Int
        val right: Int
        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            c.clipRect(
                left, parent.paddingTop, right,
                parent.height - parent.paddingBottom
            )
        } else {
            left = 0
            right = parent.width
        }
        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, mBounds)
            val bottom: Int = mBounds.bottom + child.translationY.roundToInt()
            val top: Int = bottom - mDivider.intrinsicHeight
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
        c.restore()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) == state.itemCount - 1) {
            outRect.setEmpty()
        } else {
            if (mDivider == null) {
                outRect.setEmpty()
                return
            } else {
                outRect.set(0, 0, 0, mDivider.intrinsicHeight)
            }
        }
    }
}