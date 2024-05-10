package ru.netology.weatherapp.extensions

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

inline fun RecyclerView.addItemDecoration(
    crossinline action: (
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) -> Unit
) {
    addItemDecoration(
        object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State,
            ) {
                action(outRect, view, parent, state)
            }
        }
    )
}
