package com.example.gamergames.presentation.views.recycler_view.my_games_recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.gamergames.R
import com.google.android.material.button.MaterialButton

class MyGamesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title: TextView = view.findViewById(R.id.main_recycler_view_title)
    val image: ImageView = view.findViewById(R.id.iv_game_releases_row)
    val cardView: CardView = view.findViewById(R.id.games_card_view)
}