package com.example.gamergames.presentation.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.gamergames.R
import com.example.gamergames.data.model.firebase.bos.GameBO
import com.example.gamergames.data.model.firebase.bos.MyListBO
import com.example.gamergames.domain.usecases.firebase.params.AddToLikedGames
import com.example.gamergames.presentation.views.interfaces.Navigation
import com.example.gamergames.presentation.views.recycler_view.game_details_recycler.GameDetailsAdapter
import com.example.gamergames.presentation.views.recycler_view.game_details_recycler.GameDetailsListener
import com.example.gamergames.presentation.views.viewmodels.MyInfoGamesFragmentViewModel

class GameDetailsFragment : Fragment(), Navigation, OnClickListener {

    /**Fragment where the user can get extra info about the selected game*/
    private val myInfoGamesFragmentViewModel by lazy {
        ViewModelProvider(this)[MyInfoGamesFragmentViewModel::class.java]
    }
    private var gameDetailsRecyclerView: RecyclerView? = null
    private val pagerSnapHelper: PagerSnapHelper = PagerSnapHelper()
    private var isAnimated: Boolean? = null
    private var gameId: String? = null
    private var lottieAnimationView: LottieAnimationView? =null

    /**Companion objet where whe have a fun to save a game into the bundle, by doing this we
     * prevent memory leaks on screen rotations*/
    companion object {
        private const val KEY_IMAGE_URL = "KEY_IMAGE_URL"
        private const val KEY_GAME_TITLE = "GAME_TITLE"
        private const val KEY_GAME_DESCRIPTION = "GAME_DESCRIPTION"
        private const val KEY_GAME_PLATFORM = "GAME_PLATFORM"
        private const val KEY_GAME_GENRE = "GAME_GENRE"
        private const val KEY_GAME_YEAR = "GAME_YEAR"
        private const val KEY_GAME_ID = "GAME_ID"
        private const val KEY_GAME_SCREENSHOTS = "GAME_SCREENSHOTS"
        private const val KEY_GAME_PLAYTIME = "GAME_PLAY_TIME"
        private const val KEY_GAME_RATING = "GAME_RATING"
        private const val KEY_GAME_LIKED = "LIKED_GAME"

        fun newInstance(game: GameBO): GameDetailsFragment {
            val fragment = GameDetailsFragment()
            val bundle = Bundle()
            bundle.putString(KEY_IMAGE_URL, game.image)
            bundle.putString(KEY_GAME_TITLE, game.title)
            bundle.putString(KEY_GAME_DESCRIPTION, game.description)
            bundle.putString(KEY_GAME_GENRE, game.genre)
            bundle.putString(KEY_GAME_PLATFORM, game.platform)
            bundle.putString(KEY_GAME_YEAR, game.gameYear)
            bundle.putString(KEY_GAME_ID, game.gameId)
            bundle.putDouble(KEY_GAME_RATING, game.rating)
            bundle.putInt(KEY_GAME_PLAYTIME, game.playtime)
            bundle.putBoolean(KEY_GAME_LIKED, game.myList.booleanValue)
            bundle.putStringArrayList(KEY_GAME_SCREENSHOTS, ArrayList(game.screenshots.screenshot))
            fragment.arguments = bundle
            return fragment
        }
    }

    private val onClickImage = object : GameDetailsListener {
        override fun onImageClick(gameImage: String, position: Int) {
            val imageDetailDialogFragment = ImageDetailDialogFragment(gameImage)
            imageDetailDialogFragment.show(childFragmentManager, "")
        }
    }
    private val gameDetailsAdapter = GameDetailsAdapter(onClickImage)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.let { args ->
            val imageUrl = args.getString(KEY_IMAGE_URL, "")
            val screenshotsList = args.getStringArrayList(KEY_GAME_SCREENSHOTS) ?: listOf()
            gameDetailsAdapter.getData(imageUrl, screenshotsList)

            val tvGameTitle = view.findViewById<TextView>(R.id.tv_title)
            tvGameTitle.text = args.getString(KEY_GAME_TITLE, "")

            val tvGameDescription = view.findViewById<TextView>(R.id.child_fragment_description)
            tvGameDescription.text = args.getString(KEY_GAME_DESCRIPTION, "")

            val tvGamePlatform = view.findViewById<TextView>(R.id.tv_platforms)
            tvGamePlatform.text = args.getString(KEY_GAME_PLATFORM, "")

            val tvGameYear = view.findViewById<TextView>(R.id.tv_year)
            tvGameYear.text = args.getString(KEY_GAME_YEAR, "")

            val tvPlaytime = view.findViewById<TextView>(R.id.tv_playtime)
            tvPlaytime.text = args.getInt(KEY_GAME_PLAYTIME).toString()

            val tvRating = view.findViewById<TextView>(R.id.tv_rating)
            tvRating.text = args.getDouble(KEY_GAME_RATING).toString()

            val tvGenres = view.findViewById<TextView>(R.id.tv_genres)
            tvGenres.text = args.getString(KEY_GAME_GENRE, "")

            isAnimated = args.getBoolean(KEY_GAME_LIKED)
            gameId = args.getString(KEY_GAME_ID, "")
            lottieAnimationView = view.findViewById(R.id.btn_liked_game)

            setLikedButtonFrames(lottieAnimationView!!)
            lottieAnimationView?.setOnClickListener(this)


        }
        gameDetailsRecyclerView = view.findViewById(R.id.child_fragment_image_games_carousel)
        gameDetailsRecyclerView?.adapter = gameDetailsAdapter
        gameDetailsRecyclerView?.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)

        pagerSnapHelper.attachToRecyclerView(gameDetailsRecyclerView)

        onBackPressed()
    }

    private fun setLikedButtonFrames(lottieAnimationView: LottieAnimationView) {
        if (isAnimated!!) {
            lottieAnimationView.frame = 17
        } else {
            lottieAnimationView.frame = 0
        }
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

    override fun onClick(view: View?) {
        lottieAnimationView?.setOnClickListener {
            if (isAnimated!!){
                lottieAnimationView?.setMinAndMaxFrame(17,28)
                lottieAnimationView?.playAnimation()
                myInfoGamesFragmentViewModel.addGameToMyList(
                    gameId ?: "", AddToLikedGames(
                        MyListBO(false)
                    )
                )
                isAnimated = false
            } else {
                lottieAnimationView?.setMinAndMaxFrame(0,15)
                lottieAnimationView?.playAnimation()
                myInfoGamesFragmentViewModel.addGameToMyList(
                    gameId ?: "", AddToLikedGames(
                        MyListBO(true)
                    )
                )
                isAnimated = true
            }
        }
    }
}