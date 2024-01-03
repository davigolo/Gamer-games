package com.example.gamergames.presentation.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamergames.AsyncResult
import com.example.gamergames.R
import com.example.gamergames.data.model.firebase.bos.GameBO
import com.example.gamergames.showErrorText
import com.example.gamergames.showExceptionText
import com.example.gamergames.presentation.views.interfaces.MyGamesAdapterListener
import com.example.gamergames.presentation.views.interfaces.Navigation
import com.example.gamergames.presentation.views.main_activity.MainActivity
import com.example.gamergames.presentation.views.recycler_view.my_games_recyclerview.GamesRecyclerItemDecoration
import com.example.gamergames.presentation.views.recycler_view.my_games_recyclerview.MyGamesAdapter
import com.example.gamergames.presentation.views.viewmodels.MyGamesFragmentViewModel
import com.example.gamergames.presentation.views.viewmodels.SharedDataViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlin.system.exitProcess


class MyGamesFragment : Fragment(), Navigation {

    /**Fragment where we display the games added to the personal list by the user*/
    companion object {
        private const val KEY_GAME_ID_TO_REMOVE = "KEY_GAME_ID_TO_REMOVE"
    }

    //region Private values
    private var gamesMainRecyclerView: RecyclerView? = null
    private var myGamesLoadingProgressBar: ProgressBar? = null
    private var mainFragmentFloatingButton: FloatingActionButton? = null
    private val mainViewModel by lazy { ViewModelProvider(this)[MyGamesFragmentViewModel::class.java] }
    private val sharedDataViewModel by lazy { ViewModelProvider(requireActivity())[SharedDataViewModel::class.java] }

    private var selectedGameIdToRemove: String? = null

    private val onClickRemoveGame = View.OnClickListener {
        selectedGameIdToRemove?.let { id ->
            mainViewModel.removeGame(id)
        }
    }

    private val onClickGame = object : MyGamesAdapterListener {
        override fun onClickGame(game: GameBO) {
            activity?.let { currentActivity ->
                val fragment = GameDetailsFragment.newInstance(game)
                (currentActivity as? Navigation)?.navigateTo(fragment)

                sharedDataViewModel.isGameClicked(true)
            }
        }

        override fun deleteGame(view: View, gameBO: GameBO): Boolean {
            selectedGameIdToRemove = gameBO.gameId
            val removeSnackbar = Snackbar.make(
                view,
                getString(R.string.delete_game_snackbar),
                Snackbar.LENGTH_LONG
            )
            removeSnackbar.setAction(getString(R.string.delete), onClickRemoveGame)
            removeSnackbar.show()
            return true
        }

    }

    private val gamesAdapter = MyGamesAdapter(onClickGame)

    private val gameListObserver = Observer<AsyncResult<List<GameBO>>> { asyncResult ->
        when (asyncResult.state) {
            AsyncResult.STATE.SUCCESS -> {
                gamesAdapter.submitList(asyncResult.data)
                hideProgressBar()
            }
            AsyncResult.STATE.ERROR -> {
                showErrorText(asyncResult.error, context)
                hideProgressBar()
            }
            else -> {
                showExceptionText(asyncResult.exception.toString(), context)
                hideProgressBar()
            }
        }
    }

    private val onSuccessRemoveGame = Observer<String> { id ->
        val newList = gamesAdapter.currentList.filter { item -> item.gameId != id }
        gamesAdapter.submitList(newList)

    }
    private val dataLoadingStateObserver = Observer<Boolean> { isDataLoaded ->
        if (isDataLoaded){
            myGamesLoadingProgressBar?.visibility = View.INVISIBLE
        }
    }
    //endregion

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        savedInstanceState?.let { bundle ->
            selectedGameIdToRemove = bundle.getString(KEY_GAME_ID_TO_REMOVE, null)
        }
        return inflater.inflate(R.layout.fragment_my_games, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myGamesLoadingProgressBar = view.findViewById(R.id.games_list_progress_bar)
        mainFragmentFloatingButton = view.findViewById(R.id.main_fragment_floating_button)
        gamesMainRecyclerView = view.findViewById(R.id.games_main_recycler_view)

        gamesMainRecyclerView?.addItemDecoration(GamesRecyclerItemDecoration())
        gamesMainRecyclerView?.layoutManager = GridLayoutManager(context, 2)
        gamesMainRecyclerView?.adapter = gamesAdapter

        mainViewModel.getOnSuccessRemoveGameLiveData()
            .observe(viewLifecycleOwner, onSuccessRemoveGame)
        mainViewModel.requestGamesList()
        mainViewModel.getGamesLiveData().observe(viewLifecycleOwner, gameListObserver)

        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mainFragmentFloatingButton?.setOnClickListener {
            activity?.let { currentActivity ->
                val fragment = AddGameFragment.newInstance()
                (currentActivity as? Navigation)?.navigateTo(fragment)
                MainActivity().topAppBarTitle?.text = getText(R.string.add_new_games_fragment_title)
            }

        }
        onBackPressed()
    }

    private fun onBackPressed(){
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    sharedDataViewModel.isGameClicked(false)
                    exitProcess(0)
                }
            })
    }
    private fun hideProgressBar(){
        mainViewModel.getDataLoadingState().observe(viewLifecycleOwner, dataLoadingStateObserver)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        selectedGameIdToRemove?.let { id ->
            outState.putString(KEY_GAME_ID_TO_REMOVE, id)
        }
        super.onSaveInstanceState(outState)
    }


    override fun navigateTo(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.flContent, fragment).commit()
    }
}