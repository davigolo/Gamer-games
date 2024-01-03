package com.example.gamergames.presentation.views.recycler_view.searched_games_recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SearchedGamesRecyclerItemDecoration: RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = 20
        outRect.top = 20
        outRect.left = 20
        outRect.right = 20
    }


}