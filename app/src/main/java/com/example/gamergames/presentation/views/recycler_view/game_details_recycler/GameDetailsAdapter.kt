package com.example.gamergames.presentation.views.recycler_view.game_details_recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.gamergames.R
import com.example.gamergames.loadUrl

class GameDetailsAdapter(private val onClickListener: GameDetailsListener) :
    RecyclerView.Adapter<GameDetailsViewHolder>() {

    val gameImagesList: ArrayList<String> = arrayListOf()

    fun getData(mainImage: String, screenshotsList: List<String>) {
        gameImagesList.add(mainImage)
        gameImagesList.addAll(screenshotsList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameDetailsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_game_details_images, parent, false)
        return GameDetailsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return gameImagesList.size
    }

    override fun onBindViewHolder(holder: GameDetailsViewHolder, position: Int) {
        val selectedGameImage = gameImagesList[position]
        holder.gameImage.loadUrl(selectedGameImage)
        holder.itemView.setOnClickListener{
            onClickListener.onImageClick(selectedGameImage, position)
        }
        holder.gameDetailsImageCardView.startAnimation(
            AnimationUtils.loadAnimation(
                holder.itemView.context, R.anim.recycler_view_animation
            )
        )
    }
}