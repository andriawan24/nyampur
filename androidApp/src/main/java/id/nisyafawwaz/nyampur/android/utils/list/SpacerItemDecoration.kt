package id.nisyafawwaz.nyampur.android.utils.list

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import id.nisyafawwaz.nyampur.android.utils.extensions.px

class SpacerItemDecoration(
    private val gap: Int = 24.px,
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

        if (position < totalCount - 1) {
            outRect.bottom = gap
        }
    }
}
