package com.example.gamergames.presentation.views.recycler_view.searched_games_recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.gamergames.R
import com.example.gamergames.data.model.rawg.bos.AddGameBo
import com.example.gamergames.data.model.rawg.bos.GameDetailsBO
import com.example.gamergames.data.model.rawg.bos.GameScreenshotsBO
import com.example.gamergames.presentation.views.interfaces.SearchedGamesAdapterListener
import com.example.gamergames.loadUrl

class SearchedGamesAdapter(private val listener: SearchedGamesAdapterListener) :
    ListAdapter<AddGameBo, SearchedGamesViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedGamesViewHolder {
            val itemView =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_searched_games_list, parent, false)
            return SearchedGamesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchedGamesViewHolder, position: Int) {
        val game = currentList[position]
        holder.gameTitle.text = game.title
        holder.gameImage.loadUrl(game.imageUrl)
        holder.itemView.setOnClickListener {
            listener.onGameClick(
                GameDetailsBO(
                    game.gameId,
                    game.title,
                    "",
                    game.year,
                    listOf(),
                    game.imageUrl,
                    listOf(),
                    999,
                    999,
                    listOf()
                ), GameScreenshotsBO(
                    listOf()
                )
            )
        }
    }
    private object DiffUtilCallback : DiffUtil.ItemCallback<AddGameBo>() {
        override fun areItemsTheSame(oldItem: AddGameBo, newItem: AddGameBo): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: AddGameBo, newItem: AddGameBo): Boolean =
            oldItem == newItem

    }
}