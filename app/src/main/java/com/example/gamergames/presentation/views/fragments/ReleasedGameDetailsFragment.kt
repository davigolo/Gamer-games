package com.example.gamergames.presentation.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.gamergames.R
import com.example.gamergames.data.model.rawg.bos.RawgGameBO
import com.example.gamergames.presentation.views.interfaces.Navigation
import com.example.gamergames.presentation.views.login.main_login_window.MainLoginFragment
import com.example.gamergames.presentation.views.recycler_view.game_details_recycler.GameDetailsAdapter
import com.example.gamergames.presentation.views.recycler_view.game_details_recycler.GameDetailsListener

class ReleasedGameDetailsFragment() : Fragment(), Navigation {

    /**Fragment to display details from a game*/
    //region Private values
    private val onClickImage = object : GameDetailsListener {
        override fun onImageClick(gameImage: String, position: Int) {
            val imageDetailDialogFragment = ImageDetailDialogFragment(gameImage)
            imageDetailDialogFragment.show(childFragmentManager, "")
        }
    }
    private val gameDetailsAdapter = GameDetailsAdapter(onClickImage)
    private var gameDetailsRecyclerView: RecyclerView? = null
    private  val pagerSnapHelper: PagerSnapHelper = PagerSnapHelper()
    //endregion

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
        fun newInstance(data: RawgGameBO): ReleasedGameDetailsFragment {
            val fragment = ReleasedGameDetailsFragment()
            val bundle = Bundle()
            bundle.putString(KEY_IMAGE_URL, data.gameDetailsBO?.image)
            bundle.putString(KEY_GAME_TITLE, data.gameDetailsBO?.name)
            bundle.putString(KEY_GAME_DESCRIPTION, data.gameDetailsBO?.description)
            bundle.putStringArrayList(KEY_GAME_GENRE,
                data.gameDetailsBO?.genres?.let { ArrayList(it) })
            bundle.putStringArrayList(KEY_GAME_PLATFORM,
                data.gameDetailsBO?.platforms?.let { ArrayList(it) })
            bundle.putString(KEY_GAME_YEAR, data.gameDetailsBO?.gameYear)
            bundle.putString(KEY_GAME_ID, data.gameDetailsBO?.id)
            data.gameDetailsBO?.rating?.let { bundle.putInt(KEY_GAME_RATING, it) }
            data.gameDetailsBO?.playtime?.let { bundle.putInt(KEY_GAME_PLAYTIME, it) }
            bundle.putStringArrayList(KEY_GAME_SCREENSHOTS,
                data.gameScreenshotsBO?.screenshotUrl?.map { it.screenshot }
                    ?.let { ArrayList(it) })
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { args ->
            val imageUrl = args.getString(KEY_IMAGE_URL, "")
            val screenshotsList = args.getStringArrayList(KEY_GAME_SCREENSHOTS) ?: listOf()
            gameDetailsAdapter.getData(imageUrl, screenshotsList)

            val tvGameTitle = view.findViewById<TextView>(R.id.tv_title)
            tvGameTitle.text = args.getString(KEY_GAME_TITLE, "")

            val tvGameDescription = view.findViewById<TextView>(R.id.child_fragment_description)
            tvGameDescription.text = args.getString(KEY_GAME_DESCRIPTION, "")

            val tvGamePlatform = view.findViewById<TextView>(R.id.tv_platforms)
            args.getStringArrayList(KEY_GAME_PLATFORM)?.let { gamesPlatformsList ->
                printList(gamesPlatformsList, tvGamePlatform)
            }

            val tvGameYear = view.findViewById<TextView>(R.id.tv_year)
            tvGameYear.text = args.getString(KEY_GAME_YEAR, "")

            val tvPlaytime = view.findViewById<TextView>(R.id.tv_playtime)
            tvPlaytime.text = args.getInt(KEY_GAME_PLAYTIME).toString()

            val tvRating = view.findViewById<TextView>(R.id.tv_rating)
            tvRating.text = args.getInt(KEY_GAME_RATING).toString()

            val tvGenres = view.findViewById<TextView>(R.id.tv_genres)
            args.getStringArrayList(KEY_GAME_GENRE)?.let { gameGenresList ->
                printList(gameGenresList, tvGenres)
            }
        }


        gameDetailsRecyclerView = view.findViewById(R.id.child_fragment_image_games_carousel)
        gameDetailsRecyclerView?.adapter = gameDetailsAdapter
        gameDetailsRecyclerView?.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)

        pagerSnapHelper.attachToRecyclerView(gameDetailsRecyclerView)

        onBackPressed()
    }

    //region Private methods
    private fun onBackPressed(){
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.let { fragment ->
                        fragment.supportFragmentManager.beginTransaction().remove(ReleasedGameDetailsFragment()).commit()
                    }
                }
            })
    }

    private fun printList(data: List<String>, textView: TextView) {
        var counter = 0
        for (value in data) {
            if (data.size > 1) {
                counter++
                if (counter == (data.size)) {
                    textView.text = textView.text.toString().plus("$value ")
                } else {
                    textView.text = textView.text.toString().plus("$value, ")
                }
            } else {
                textView.text = value
            }
        }
    }

    override fun navigateTo(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.flContent, fragment).commit()
    }
    //endregion

}
