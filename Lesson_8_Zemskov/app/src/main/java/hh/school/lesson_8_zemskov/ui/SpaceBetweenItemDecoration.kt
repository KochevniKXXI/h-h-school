package hh.school.lesson_8_zemskov.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.carousel.CarouselLayoutManager

/**
 * Равноудаляет элементамы <code>RecyclerView</code> между собой.
 *
 * @param spacerSize Расстояние в пикселях.
 */

class SpaceBetweenItemDecoration (
    private val spacerSize: Int
) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutManager = parent.layoutManager
        val childPosition = layoutManager?.getPosition(view) ?: 0
        var (spanIndex, spanCount, orientation) = listOf(0, 1, VERTICAL)
        when (layoutManager) {
            is GridLayoutManager -> {
                spanIndex = (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex
                spanCount = layoutManager.spanCount
                orientation = layoutManager.orientation
            }
            is LinearLayoutManager -> {
                orientation = layoutManager.orientation
            }
            is StaggeredGridLayoutManager -> {
                spanIndex = (view.layoutParams as StaggeredGridLayoutManager.LayoutParams).spanIndex
                spanCount = layoutManager.spanCount
                orientation = layoutManager.orientation
            }
            is CarouselLayoutManager -> {
                orientation = layoutManager.orientation
            }
            else -> {}
        }

        if (orientation == VERTICAL) {
            if (childPosition >= spanCount) outRect.top = spacerSize
            if ((spanIndex + 1) % spanCount != 0) outRect.right = spacerSize
        } else {
            if (childPosition >= spanCount) outRect.left = spacerSize
            if ((spanIndex + 1) % spanCount != 0) outRect.bottom = spacerSize
        }
    }
}