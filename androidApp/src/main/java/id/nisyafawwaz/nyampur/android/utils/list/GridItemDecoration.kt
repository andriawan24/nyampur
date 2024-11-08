package id.nisyafawwaz.nyampur.android.utils.list

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import id.nisyafawwaz.nyampur.android.utils.extensions.px

class GridItemDecoration(
    private val gridCount: Int,
    private val verticalMargin: Int = 24.px,
    private val horizontalMargin: Int = 16.px,
) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val totalCount = state.itemCount

        outRect.top = verticalMargin

        val index = position % gridCount
        when (index) {
            0 -> {
                outRect.left = verticalMargin
                outRect.right = horizontalMargin / 2
            }

            gridCount - 1 -> {
                outRect.left = horizontalMargin / 2
                outRect.right = verticalMargin
            }

            else -> {
                outRect.left = horizontalMargin / 2
                outRect.right = horizontalMargin / 2
            }
        }

        if (position > totalCount - gridCount - 1) {
            outRect.bottom = verticalMargin
        }
    }
}
