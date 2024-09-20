package com.example.moodtracker.ui.home.list

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MoodItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        // Добавляем отступ снизу для всех элементов, кроме последнего
        if (parent.getChildAdapterPosition(view) != parent.adapter!!.itemCount - 1) {
            outRect.bottom = space
        }
    }
}