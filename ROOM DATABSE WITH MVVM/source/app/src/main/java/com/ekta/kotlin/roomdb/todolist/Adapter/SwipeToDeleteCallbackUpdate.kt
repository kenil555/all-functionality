//package com.ekta.kotlin.roomdb.todolist.Adapter
//
//import android.content.Context
//import android.graphics.Canvas
//import android.graphics.Color
//import android.graphics.Paint
//import android.graphics.PorterDuff
//import android.graphics.PorterDuffXfermode
//import android.graphics.drawable.ColorDrawable
//import android.graphics.drawable.Drawable
//import androidx.core.content.ContextCompat
//import androidx.recyclerview.widget.ItemTouchHelper
//import androidx.recyclerview.widget.RecyclerView
//import com.ekta.kotlin.roomdb.todolist.R
//
//abstract class SwipeToDeleteCallbackUpdate internal constructor(var mContext: Context) :
//    ItemTouchHelper.Callback() {
//    private val mClearPaint: Paint
//    private val mBackground: ColorDrawable
//    private val backgroundColorDelete: Int
//    private val backgroundColorUpdate: Int
//    private val deleteDrawable: Drawable?
//    private val updateDrawable: Drawable?
//    private val intrinsicWidth: Int
//    private val intrinsicHeight: Int
//
//    init {
//        mBackground = ColorDrawable()
//        backgroundColorDelete = Color.parseColor("#b80f0a")
//        backgroundColorUpdate = Color.parseColor("#FFA500") // Orange color for update
//        mClearPaint = Paint().apply {
//            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
//        }
//        deleteDrawable = ContextCompat.getDrawable(mContext, R.drawable.baseline_delete_24)
//        updateDrawable = ContextCompat.getDrawable(mContext, R.drawable.baseline_edit)
//        intrinsicWidth = deleteDrawable!!.intrinsicWidth
//        intrinsicHeight = deleteDrawable.intrinsicHeight
//    }
//
//    override fun getMovementFlags(
//        recyclerView: RecyclerView,
//        viewHolder: RecyclerView.ViewHolder
//    ): Int {
//        return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
//    }
//
//    override fun onMove(
//        recyclerView: RecyclerView,
//        viewHolder: RecyclerView.ViewHolder,
//        target: RecyclerView.ViewHolder
//    ): Boolean {
//        return false
//    }
//
//    override fun onChildDraw(
//        c: Canvas,
//        recyclerView: RecyclerView,
//        viewHolder: RecyclerView.ViewHolder,
//        dX: Float,
//        dY: Float,
//        actionState: Int,
//        isCurrentlyActive: Boolean
//    ) {
//        val itemView = viewHolder.itemView
//        val itemHeight = itemView.height
//        val isCancelled = dX == 0f && !isCurrentlyActive
//
//        if (isCancelled) {
//            clearCanvas(c, itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
//            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//            return
//        }
//
//        if (dX > 0) { // Swiping to the right (Update)
//            mBackground.color = backgroundColorUpdate
//            mBackground.setBounds(itemView.left, itemView.top, itemView.left + dX.toInt(), itemView.bottom)
//            mBackground.draw(c)
//
//            val updateIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
//            val updateIconMargin = (itemHeight - intrinsicHeight) / 2
//            val updateIconLeft = itemView.left + updateIconMargin
//            val updateIconRight = itemView.left + updateIconMargin + intrinsicWidth
//            val updateIconBottom = updateIconTop + intrinsicHeight
//
//            updateDrawable!!.setBounds(updateIconLeft, updateIconTop, updateIconRight, updateIconBottom)
//            updateDrawable.draw(c)
//        } else if (dX < 0) { // Swiping to the left (Delete)
//            mBackground.color = backgroundColorDelete
//            mBackground.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
//            mBackground.draw(c)
//
//            val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
//            val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
//            val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth
//            val deleteIconRight = itemView.right - deleteIconMargin
//            val deleteIconBottom = deleteIconTop + intrinsicHeight
//
//            deleteDrawable!!.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
//            deleteDrawable.draw(c)
//        }
//
//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//    }
//
//    private fun clearCanvas(c: Canvas, left: Float, top: Float, right: Float, bottom: Float) {
//        c.drawRect(left, top, right, bottom, mClearPaint)
//    }
//
//    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
//        return 0.7f
//    }
//}