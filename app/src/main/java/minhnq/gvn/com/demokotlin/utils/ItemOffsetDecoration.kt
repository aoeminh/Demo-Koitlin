package minhnq.gvn.com.demokotlin.utils

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class ItemOffsetDecoration(context: Context?, itemOffsetId: Int) : RecyclerView.ItemDecoration() {
    var mItemOffset: Int? = null

    init {
        this.mItemOffset = itemOffsetId
        context?.resources?.getDimensionPixelSize(itemOffsetId)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        mItemOffset?.let { outRect.set(it,it,it,it) }
    }
}