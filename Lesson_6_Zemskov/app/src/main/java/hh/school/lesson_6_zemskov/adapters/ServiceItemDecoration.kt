package hh.school.lesson_6_zemskov.adapters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class ServiceItemDecoration(
    private val space: Int
) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.bottom = space
        outRect.left = space
        outRect.right = space
        if (parent.getChildLayoutPosition(view) == 0) outRect.top = space
    }
}