package com.example.gamergames.presentation.views.recycler_view.last_month_releases

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.gamergames.data.model.rawg.bos.RawgGameBO
import androidx.recyclerview.widget.ListAdapter
import com.example.gamergames.R
import com.example.gamergames.presentation.views.interfaces.GamesListener
import com.example.gamergames.loadUrl


class LastMonthReleasesAdapter(private val listener: GamesListener) :
    ListAdapter<RawgGameBO, LastMonthReleasesViewHolder>(DiffUtilCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastMonthReleasesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_last_month_releases, parent, false)
        return LastMonthReleasesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LastMonthReleasesViewHolder, position: Int) {
        val game = currentList[position]
        holder.gameReleasesImage.loadUrl(game.gameDetailsBO?.image ?: "")
        holder.gameReleasesTextView.text = game.gameDetailsBO?.name
        holder.itemView.setOnClickListener{
            listener.receiveGame(game)
        }
    }

    //GetItemCount no hace falta sobreescribrilo, coge por defecto el tamño de la lista
    private object DiffUtilCallback : DiffUtil.ItemCallback<RawgGameBO>() {
        override fun areItemsTheSame(oldItem: RawgGameBO, newItem: RawgGameBO): Boolean =
            oldItem.gameDetailsBO?.id == newItem.gameDetailsBO?.id

        override fun areContentsTheSame(oldItem: RawgGameBO, newItem: RawgGameBO): Boolean =
            oldItem == newItem

    }
}