package com.example.gamergames.presentation.views.recycler_view.searched_games_recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gamergames.R

class SearchedGamesViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val gameImage: ImageView = view.findViewById(R.id.row_movies_image_recycler_view)
    val gameTitle: TextView = view.findViewById(R.id.row_movie_title_recycler_view)
}