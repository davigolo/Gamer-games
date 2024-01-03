package com.example.gamergames.presentation.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamergames.AsyncResult
import com.example.gamergames.R
import com.example.gamergames.data.model.rawg.bos.RawgGameBO
import com.example.gamergames.presentation.views.interfaces.GamesListener
import com.example.gamergames.presentation.views.interfaces.Navigation
import com.example.gamergames.presentation.views.main_activity.MainActivity
import com.example.gamergames.presentation.views.recycler_view.last_month_releases.LastMonthReleasesAdapter
import com.example.gamergames.presentation.views.recycler_view.last_month_releases.LastMonthReleasesItemDecoration
import com.example.gamergames.presentation.views.viewmodels.LastMonthReleasesFragmentViewModel
import com.example.gamergames.showErrorText
import com.example.gamergames.showExceptionText

class LastMonthReleasesFragment(private val clickedOption: String) : Fragment(), Navigation {

    /**Fragment where we receive the clicked drawer option, a recyclerView is displayed with data according
     * to the clicked option */
    private val gamesByDateViewModel by lazy { ViewModelProvider(this)[LastMonthReleasesFragmentViewModel::class.java] }
    private val onGameClick = object : GamesListener {
        override fun receiveGame(data: RawgGameBO) {
            activity?.let { currentActivity ->
                val fragment = ReleasedGameDetailsFragment.newInstance(data)
                (currentActivity as? Navigation)?.navigateTo(fragment)
            }
        }
    }
    private val lastMonthReleasesAdapter = LastMonthReleasesAdapter(onGameClick)
    private var loadingProgressBar: ProgressBar? = null
    private var recyclerViewLastMonthReleases: RecyclerView? = null
    private var noGamesWindow: LinearLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_last_month_days_releases, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noGamesWindow = view.findViewById(R.id.no_current_games_anim)
        loadingProgressBar = view.findViewById(R.id.last_month_releases_progress_bar)

        recyclerViewLastMonthReleases = view.findViewById(R.id.rv_last_month_releases)
        recyclerViewLastMonthReleases?.adapter = lastMonthReleasesAdapter
        recyclerViewLastMonthReleases?.layoutManager = GridLayoutManager(context, 2)
        recyclerViewLastMonthReleases?.addItemDecoration(LastMonthReleasesItemDecoration())

        when (clickedOption) {
            MainActivity.ON_NEXT_WEEK_RELEASES_CLICK -> onNextWeekReleasesClick()
            MainActivity.ON_WEEKLY_RELEASES_CLICK -> onWeeklyReleasesClick()
            MainActivity.ON_LAST_MONTH_RELEASES_CLICK -> onLastMonthReleasesClick()
            MainActivity.ON_TOP_GAMES_CLICK -> onTopGamesClick()
            MainActivity.ON_BEST_OF_THE_YEAR_CLICK -> onBestOfTheYearClick()
        }
        onBackPressed()
    }

    //region Private methods
    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val fragment: Fragment = MyGamesFragment()
                    navigateTo(fragment)
                }
            })
    }

    private fun onNextWeekReleasesClick() {
        val nextWeekDateInterval = gamesByDateViewModel.requestNextWeekDateInterval()
        gamesByDateViewModel.requestGamesByDate(
            nextWeekDateInterval,
            "-" + OrderGamesBy.RELEASED.toString()
        )
        gamesByDateViewModel.getGameListByDate().observe(viewLifecycleOwner) { asyncResult ->
            handleAsyncResult(asyncResult, lastMonthReleasesAdapter)
        }
        hideProgressBar()
    }

    private fun onWeeklyReleasesClick() {
        val weeklyDateInterval = gamesByDateViewModel.requestWeeklyDateInterval()
        gamesByDateViewModel.requestGamesByDate(
            weeklyDateInterval,
            "-" + OrderGamesBy.RELEASED.toString()
        )
        gamesByDateViewModel.getGameListByDate().observe(viewLifecycleOwner) { asyncResult ->
            handleAsyncResult(asyncResult, lastMonthReleasesAdapter)
        }
        hideProgressBar()
    }

    private fun onLastMonthReleasesClick() {
        val lastMonthDateInterval = gamesByDateViewModel.requestLastMonthDateInterval()
        gamesByDateViewModel.requestGamesByDate(
            lastMonthDateInterval,
            "-" + OrderGamesBy.RELEASED.toString()
        )
        gamesByDateViewModel.getGameListByDate().observe(viewLifecycleOwner) { asyncResult ->
            handleAsyncResult(asyncResult, lastMonthReleasesAdapter)
        }
        hideProgressBar()
    }

    private fun onTopGamesClick() {
        val currentDate = gamesByDateViewModel.requestCurrentDate()
        gamesByDateViewModel.requestGamesByDate(
            currentDate,
            "-" + OrderGamesBy.RATING.toString()
        )
        gamesByDateViewModel.getGameListByDate().observe(viewLifecycleOwner) { asyncResult ->
            handleAsyncResult(asyncResult, lastMonthReleasesAdapter)
        }
        hideProgressBar()
    }

    private fun onBestOfTheYearClick() {
        val yearInterval = gamesByDateViewModel.requestYearInterval()
        gamesByDateViewModel.requestGamesByDate(
            yearInterval,
            "-" + OrderGamesBy.RATING.toString()
        )
        gamesByDateViewModel.getGameListByDate().observe(viewLifecycleOwner) { asyncResult ->
            handleAsyncResult(asyncResult, lastMonthReleasesAdapter)
        }
        hideProgressBar()
    }

    private fun hideProgressBar() {

        gamesByDateViewModel.getDataLoadingState().observe(viewLifecycleOwner) { isDataLoaded ->
            if (isDataLoaded) {
                loadingProgressBar?.visibility = View.INVISIBLE
                if (lastMonthReleasesAdapter.itemCount == 0) {
                    noGamesWindow?.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun handleAsyncResult(
        asyncResult: AsyncResult<List<RawgGameBO>>,
        adapter: LastMonthReleasesAdapter
    ) {

        when (asyncResult.state) {
            AsyncResult.STATE.SUCCESS -> {
                adapter.submitList(asyncResult.data)
                hideProgressBar()
            }

            AsyncResult.STATE.ERROR -> {
                hideProgressBar()
                showErrorText(asyncResult.error, context)
                adapter.submitList(asyncResult.data)
            }

            AsyncResult.STATE.EXCEPTION -> {
                hideProgressBar()
                showExceptionText(asyncResult.exception.toString(), context)
            }

            AsyncResult.STATE.LOADING -> loadingProgressBar?.visibility = View.VISIBLE
        }
    }

    //endregion
    override fun navigateTo(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.flContent, fragment).commit()
    }

    private enum class OrderGamesBy(private val ordering: String) {
        RELEASED("released"),
        NAME("name"),
        ADDED("added"),
        CREATED("created"),
        UPDATED("updated"),
        RATING("rating"),
        METACRITIC("metacritic");

        override fun toString(): String {
            return ordering
        }
    }
}