package hh.school.lesson_4_zemskov

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class SpaceEvenlyItemDecoration(
    @DimenRes private val dimenResId: Int
) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildLayoutPosition(view)
        val viewType = parent.adapter?.getItemViewType(position)
        val spaceEvenly = parent.context.resources.getDimensionPixelOffset(dimenResId)

        outRect.left = spaceEvenly
        outRect.bottom = spaceEvenly
        if (position < (parent.layoutManager as GridLayoutManager).spanCount) {
            outRect.top = spaceEvenly
        }
        if (position % 2 != 0 || viewType == TYPE_LIST) {
            outRect.right = spaceEvenly
        }
    }
}