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
    private val outerVerticalMargin: Int = 24.px,
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

        if (position in (0..<gridCount)) {
            outRect.top = outerVerticalMargin
        }

        val index = position % gridCount
        outRect.left = horizontalMargin * (gridCount - index) / gridCount
        outRect.right = horizontalMargin * (index + 1) / gridCount

        if (position >= totalCount - gridCount) {
            outRect.bottom = outerVerticalMargin
        } else {
            outRect.bottom = verticalMargin
        }
    }
}
