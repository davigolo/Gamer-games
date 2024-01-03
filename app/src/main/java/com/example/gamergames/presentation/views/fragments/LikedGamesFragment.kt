package com.example.gamergames.presentation.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamergames.R
import com.example.gamergames.data.model.firebase.bos.GameBO
import com.example.gamergames.presentation.views.interfaces.MyGamesAdapterListener
import com.example.gamergames.presentation.views.interfaces.Navigation
import com.example.gamergames.presentation.views.recycler_view.my_games_recyclerview.GamesRecyclerItemDecoration
import com.example.gamergames.presentation.views.recycler_view.my_games_recyclerview.MyGamesAdapter
import com.example.gamergames.presentation.views.viewmodels.LikedGamesViewModel

class LikedGamesFragment : Fragment(), Navigation {

    /**Fragment where we display liked games by the user*/
    //region Private values
    private val likedGamesViewModel by lazy { ViewModelProvider(this)[LikedGamesViewModel::class.java] }
    private val onClickGame = object : MyGamesAdapterListener {
        override fun onClickGame(game: GameBO) {
            activity?.let { currentActivity ->
                val fragment = GameDetailsFragment.newInstance(game)
                (currentActivity as? Navigation)?.navigateTo(fragment)

            }
        }

        override fun deleteGame(view: View, gameBO: GameBO): Boolean {
            return true
        }

    }
    private val gamesAdapter = MyGamesAdapter(onClickGame)
    private var addedGamesListRecyclerView: RecyclerView? = null
    //endregion
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_liked_games, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addedGamesListRecyclerView = view.findViewById(R.id.my_list_added_games)
        addedGamesListRecyclerView?.adapter = gamesAdapter
        addedGamesListRecyclerView?.addItemDecoration(GamesRecyclerItemDecoration())
        addedGamesListRecyclerView?.layoutManager = GridLayoutManager(context, 2)

        likedGamesViewModel.requestLikedGames()
        likedGamesViewModel.getLikedGamesLiveData().observe(viewLifecycleOwner) {
            //gamesAdapter.submitList(it)
        }



        onBackPressed()
    }

    private fun onBackPressed(){
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val fragment: Fragment = MyGamesFragment()
                    navigateTo(fragment)
                }
            })
    }

    override fun navigateTo(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.flContent, fragment).commit()
    }
}