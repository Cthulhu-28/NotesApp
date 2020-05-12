package cr.ac.tec.proyecto.notesapp.steven.ui.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class GridDecorator(private var spanCount : Int, private var spacing : Int, private var includeEdge : Boolean, private var headerNum : Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
//        var position = parent.getChildAdapterPosition(view) - headerNum
//        if (position >= 0) {
//            var column = position % spanCount
//            if (includeEdge) {
//                outRect.left = spacing - column * spacing / spanCount
//                outRect.right = (column + 1) * spacing / spanCount
//                if (position < spanCount) {
//                    outRect.top = spacing
//                }
//                outRect.bottom = spacing
//            } else {
//                outRect.left = column * spacing / spanCount
//                outRect.right = spacing - (column + 1) * spacing / spanCount
//                if (position >= spanCount) {
//                    outRect.top = spacing
//                }
//            }
//
//        } else {
//            outRect.left = 0
//            outRect.right = 0
//            outRect.top = 0
//            outRect.bottom = 0
//        }
        val lm = parent.layoutManager as StaggeredGridLayoutManager
        val lp = view.layoutParams as StaggeredGridLayoutManager.LayoutParams


        val spanCount = lm.spanCount
        val spanIndex = lp.spanIndex
        val position = parent.getChildAdapterPosition(view)

        if(spanIndex == 0){
            outRect.left = spacing
        }

        if(position < spanCount){
            outRect.top = spacing
        }

        outRect.right = spacing
        outRect.bottom = spacing
    }
}