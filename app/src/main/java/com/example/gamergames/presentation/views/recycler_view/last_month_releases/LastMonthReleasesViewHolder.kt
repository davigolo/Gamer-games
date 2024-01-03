package com.example.gamergames.presentation.views.recycler_view.last_month_releases

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.gamergames.R

class LastMonthReleasesViewHolder(view: View): ViewHolder(view) {

    val gameReleasesTextView = view.findViewById<TextView>(R.id.tv_game_releases_row)
    val gameReleasesImage = view.findViewById<ImageView>(R.id.iv_game_releases_row)
}