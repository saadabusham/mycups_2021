package com.raantech.mycups.utils.recycleviewutils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.raantech.mycups.R
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter

class SwipeItemTouchCallBack(
    private var adapter: BaseBindingRecyclerViewAdapter<*>,
    private val moveCallBack: MoveCallBack?
) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return if (adapter.getItem(viewHolder.bindingAdapterPosition) == null) {
            makeFlag(
                ItemTouchHelper.ACTION_STATE_SWIPE,
                ItemTouchHelper.ACTION_STATE_IDLE
            )
        } else makeFlag(
            ItemTouchHelper.ACTION_STATE_SWIPE,
            ItemTouchHelper.START
        )
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.getItem(viewHolder.bindingAdapterPosition)
            ?.let { moveCallBack?.onSwipe(it, viewHolder.bindingAdapterPosition) }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val icon: Bitmap?
        val paint = Paint()

        val itemView: View = viewHolder.itemView
        val height = itemView.bottom.toFloat() - itemView.top.toFloat()
        val width = height / 3

        if (dX < 0) {
            paint.color = adapter.context.getColor(R.color.error_color)
            val background = RectF(
                itemView.right.toFloat() + dX, itemView.top.toFloat(),
                itemView.right.toFloat(), itemView.bottom.toFloat()
            )
            c.drawRect(background, paint)
            icon = ContextCompat.getDrawable(adapter.context, R.drawable.ic_bin)?.toBitmap()
            val iconDest = RectF(
                itemView.right.toFloat() - 2 * width, itemView.top.toFloat() + width,
                itemView.right.toFloat() - width, itemView.bottom.toFloat() - width
            )
            icon?.let { c.drawBitmap(it, null, iconDest, paint) }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
    interface MoveCallBack {
        fun onSwipe(item: Any, position: Int)
    }
}