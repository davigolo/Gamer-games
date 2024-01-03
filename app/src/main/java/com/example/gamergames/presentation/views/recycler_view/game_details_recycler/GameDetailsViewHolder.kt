package com.example.gamergames.presentation.views.recycler_view.game_details_recycler

import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.gamergames.R

class GameDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val gameImage: ImageView = itemView.findViewById(R.id.game_details_image)
    val gameDetailsImageCardView: CardView = itemView.findViewById(R.id.game_details_card_view)
}