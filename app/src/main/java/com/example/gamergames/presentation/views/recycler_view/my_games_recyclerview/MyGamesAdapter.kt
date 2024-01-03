package com.example.gamergames.presentation.views.recycler_view.my_games_recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.gamergames.R
import com.example.gamergames.data.model.firebase.bos.GameBO
import com.example.gamergames.presentation.views.interfaces.MyGamesAdapterListener
import com.example.gamergames.loadUrl

class MyGamesAdapter(private val listener: MyGamesAdapterListener) :
    ListAdapter<GameBO, MyGamesViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyGamesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_my_games_recycler_view, parent, false)
        return MyGamesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyGamesViewHolder, position: Int) {
        val game = currentList[position]
        holder.title.text = game.title
        holder.image.loadUrl(game.image)
        holder.cardView.startAnimation(
            AnimationUtils.loadAnimation(
                holder.itemView.context, R.anim.recycler_view_animation
            )
        )
        holder.itemView.setOnClickListener {
            listener.onClickGame(game)
        }
        holder.itemView.setOnLongClickListener {
            listener.deleteGame(it,game)
        }
    }
}

private object DiffUtilCallback : DiffUtil.ItemCallback<GameBO>() {
    override fun areItemsTheSame(oldItem: GameBO, newItem: GameBO): Boolean =
        oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: GameBO, newItem: GameBO): Boolean = oldItem == newItem

}