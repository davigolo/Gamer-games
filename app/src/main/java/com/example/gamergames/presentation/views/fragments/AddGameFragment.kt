package com.example.gamergames.presentation.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamergames.AsyncResult
import com.example.gamergames.R
import com.example.gamergames.data.model.rawg.bos.AddGameBo
import com.example.gamergames.data.model.rawg.bos.GameDetailsBO
import com.example.gamergames.data.model.rawg.bos.GameScreenshotsBO
import com.example.gamergames.presentation.views.interfaces.Navigation
import com.example.gamergames.presentation.views.interfaces.SearchedGamesAdapterListener
import com.example.gamergames.presentation.views.recycler_view.searched_games_recycler.SearchedGamesAdapter
import com.example.gamergames.presentation.views.recycler_view.searched_games_recycler.SearchedGamesRecyclerItemDecoration
import com.example.gamergames.presentation.views.viewmodels.AddGameFragmentViewModel
import com.example.gamergames.toEditable

class AddGameFragment : Fragment(), Navigation {

    /**Frament where the user can search for games to add to the personal library*/
    companion object {
        fun newInstance(): com.example.gamergames.presentation.views.fragments.AddGameFragment {
            val fragment = com.example.gamergames.presentation.views.fragments.AddGameFragment()
            return fragment
        }
    }

    //region Private values
    private var searchGameErrorCodeText: TextView? = null
    private var gameScreeshotsList: List<String> = listOf()
    private var gameYear: String = ""
    private var platforms: List<String> = listOf()
    private var playtime: Int = 9
    private var rating: Double = 0.0
    private var genres: List<String> = listOf()
    private val addGameViewModel by lazy { ViewModelProvider(this)[AddGameFragmentViewModel::class.java] }
    private val gameListObserver = Observer<AsyncResult<List<AddGameBo>>> { asyncResult ->
        if (asyncResult.state == AsyncResult.STATE.SUCCESS) {
            gamesAdapter.submitList(asyncResult.data)
        } else if (asyncResult.state == AsyncResult.STATE.ERROR) {
            //Action on gamesList error
        }
    }
    private val gameScreenshotsObserver = Observer<AsyncResult<GameScreenshotsBO>> { asyncResult ->
        if (asyncResult.state.equals(AsyncResult.STATE.SUCCESS)) {
            asyncResult.data?.let {
                gameScreeshotsList = it.screenshotUrl.map { image -> image.screenshot }
            }
        } else if (asyncResult.state.equals(AsyncResult.STATE.ERROR)) {
           //Action on error
        }
    }
    private val movieExtraDataObserver = Observer<GameDetailsBO> { data ->
        inputGameDescription?.text = data.description.toEditable()
        gameYear = data.gameYear
        platforms = data.platforms
        playtime = data.playtime
        rating = data.rating.toDouble()
        genres = data.genres
    }
    private val onGameClick = object : SearchedGamesAdapterListener {
        override fun onGameClick(
            gameDetailsBO: GameDetailsBO,
            gameScreenshotsBO: GameScreenshotsBO
        ) {
            addGameViewModel.getGameDetails(gameDetailsBO)
            addGameViewModel.getExtraDataMovieLiveData()
                .observe(viewLifecycleOwner, movieExtraDataObserver)

            addGameViewModel.getGameScreenshots(gameDetailsBO)
            addGameViewModel.getGameScreenshotsLiveData()
                .observe(viewLifecycleOwner, gameScreenshotsObserver)


            addGameViewModel.setCurrentGame(gameDetailsBO)
            inputGameTitle?.text = gameDetailsBO.name.toEditable()
            requestedGamesByTitleRecyclerView?.visibility = View.INVISIBLE
            enableAddGameButton()
        }
    }
    private val gamesAdapter = SearchedGamesAdapter(onGameClick)
    private var requestedGamesByTitleRecyclerView: RecyclerView? = null
    private var addGameButton: Button? = null
    private var inputGameTitle: TextView? = null
    private var inputGameDescription: TextView? = null
    private var gamesSearchBar: SearchView? = null
    private var myGamesLoadingProgressBar: ProgressBar? = null
    private val dataLoadingStateObserver = Observer<Boolean> { isDataLoaded ->
        if (isDataLoaded) {
            myGamesLoadingProgressBar?.visibility = View.INVISIBLE
        }
    }
    private val errorObserver = Observer<String> { errorMessage ->
        searchGameErrorCodeText?.text = errorMessage
        searchGameErrorCodeText?.visibility = View.VISIBLE
    }
    //endregion

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_add_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myGamesLoadingProgressBar = view.findViewById(R.id.searched_games_progress_bar)
        searchGameErrorCodeText = view.findViewById(R.id.tv_error_search_game_recycler)

        requestedGamesByTitleRecyclerView = view.findViewById(R.id.imdb_movies_recycler_view)
        requestedGamesByTitleRecyclerView?.adapter = gamesAdapter
        requestedGamesByTitleRecyclerView?.layoutManager = LinearLayoutManager(context)
        requestedGamesByTitleRecyclerView?.addItemDecoration(SearchedGamesRecyclerItemDecoration())
        requestedGamesByTitleRecyclerView?.bringToFront()

        inputGameTitle = view.findViewById(R.id.input_game_title)
        inputGameDescription = view.findViewById(R.id.input_game_description)

        gamesSearchBar = view.findViewById(R.id.search_view_imdb)

        addGameViewModel.getGamesListLiveData().observe(viewLifecycleOwner, gameListObserver)

        addGameButton = view.findViewById(R.id.add_game_button)
        addGameButton?.isEnabled = false
        addGameButton?.setOnClickListener {
            val addGameFragmentViewModel = AddGameFragmentViewModel()

            addGameFragmentViewModel.addGame(
                inputGameTitle?.text.toString(),
                inputGameDescription?.text.toString(),
                addGameViewModel.getSelectedGameImage(),
                genres.joinToString { it },
                platforms.joinToString { it },
                gameYear,
                gameScreeshotsList,
                false,
                playtime,
                addGameViewModel.formatGameRating(rating)
            )
        }

        requestedGamesByTitleRecyclerView?.visibility = View.INVISIBLE
        gamesSearchBar?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                addGameViewModel.requestGameList(query)
                requestedGamesByTitleRecyclerView?.visibility = View.VISIBLE
                myGamesLoadingProgressBar?.visibility = View.VISIBLE
                myGamesLoadingProgressBar?.bringToFront()

                addGameViewModel.getDataLoadingState()
                    .observe(viewLifecycleOwner, dataLoadingStateObserver)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        onBackPressed()
    }

    //region Private methods
    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val fragment: Fragment =
                        MyGamesFragment()
                    navigateTo(fragment)
                }

            })
    }

    private fun enableAddGameButton() {
        addGameButton?.isEnabled = true
    }

    //endregion
    override fun navigateTo(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.flContent, fragment).commit()
    }
}
