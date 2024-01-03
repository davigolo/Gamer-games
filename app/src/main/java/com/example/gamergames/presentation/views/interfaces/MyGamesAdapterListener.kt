package com.example.gamergames.presentation.views.interfaces

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.gamergames.data.model.firebase.bos.GameBO

interface MyGamesAdapterListener {

    fun onClickGame(game: GameBO)

    fun deleteGame(view: View, gameBO: GameBO): Boolean

}