package com.example.gamergames.presentation.views.main_activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.gamergames.R
import com.example.gamergames.loadUrl
import com.example.gamergames.presentation.views.fragments.AddGameFragment
import com.example.gamergames.presentation.views.fragments.LastMonthReleasesFragment
import com.example.gamergames.presentation.views.fragments.LikedGamesFragment
import com.example.gamergames.presentation.views.fragments.MyGamesFragment
import com.example.gamergames.presentation.views.interfaces.Navigation
import com.example.gamergames.presentation.views.login.main_login_window.MainLoginFragment
import com.example.gamergames.presentation.views.viewmodels.MainActivityViewModel
import com.example.gamergames.presentation.views.viewmodels.SharedDataViewModel
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), Navigation {

    /**MainActivity where a drawer is displayed to navigate between fragments, these fragments are displayed inside
     * this MainActivity, clicked option in drawer is also captured here*/
    companion object {
        const val ON_LAST_MONTH_RELEASES_CLICK = "lastMonthReleases"
        const val ON_WEEKLY_RELEASES_CLICK = "weeklyReleases"
        const val ON_NEXT_WEEK_RELEASES_CLICK = "nextWeekReleases"
        const val ON_TOP_GAMES_CLICK = "topGamesClick"
        const val ON_BEST_OF_THE_YEAR_CLICK = "bestOfTheYear"
    }

    //region Private values
    var topAppBarTitle: TextView? = null
    private lateinit var drawer: DrawerLayout
    private var header: View? = null
    private var navigationView: NavigationView? = null
    private val sharedDataViewModel: SharedDataViewModel by viewModels()
    private var toolbar: Toolbar? = null
    private var profileImage: ImageView? = null
    private var userEmail: TextView? = null
    private var userName: TextView? = null
    private var signOutButton: Button? = null
    private val mainActivityViewModel by lazy { ViewModelProvider(this)[MainActivityViewModel::class.java] }
    private val userSignedInLiveDataObserver: Observer<Boolean> = Observer { signed ->
        if (!signed) {
            navigateTo(MainLoginFragment())
        } else {
            mainActivityViewModel.requestGoogleAccountData(this)
            mainActivityViewModel.getUserGoogleProfileImage().observe(this) { userProfileImage ->
                profileImage?.loadUrl(userProfileImage)
            }
            mainActivityViewModel.getUserGoogleUserEmail().observe(this) { signedUserEmail ->
                userEmail?.text = signedUserEmail
            }
            mainActivityViewModel.getUserGoogleUserName().observe(this) { signedUserName ->
                userName?.text = signedUserName
            }
        }
    }
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityViewModel.getUserSignedInStateLiveData().observe(this, userSignedInLiveDataObserver)
        navigateTo(MyGamesFragment())

        topAppBarTitle = findViewById(R.id.top_app_bar_title)
        topAppBarTitle?.text = getText(R.string.my_games_fragment_title)

        userEmail = findViewById(R.id.tv_user_email)
        userName = findViewById(R.id.tv_user_name)
        drawer = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.svg_hamburguer_menu)

        setupDrawerContent(findViewById(R.id.nvView))

        navigationView = findViewById(R.id.nvView)
        header = navigationView?.getHeaderView(0)
        userEmail = header?.findViewById(R.id.tv_user_email)
        userName = header?.findViewById(R.id.tv_user_name)
        profileImage = header?.findViewById(R.id.profile_image)

        signOutButton = findViewById(R.id.btn_log_out)
        signOutButton?.setOnClickListener {
            mainActivityViewModel.signOut()
            sharedDataViewModel.getGoogleSignInClientLiveData().observe(this) { signInIntent ->
                signInIntent.signOut()
            }
            mainActivityViewModel.requestUserSignedIn()
            mainActivityViewModel.getUserSignedInStateLiveData().observe(this) { userIsSignedIn ->
                if (userIsSignedIn) {
                    navigateTo(MainLoginFragment())
                    drawer.closeDrawers()
                }
            }
        }

        sharedDataViewModel.getGoogleSignInAccountLiveData().observe(this) { googleAccount ->
            profileImage?.loadUrl(googleAccount.photoUrl.toString())
            userEmail?.text = googleAccount.email
            userName?.text = googleAccount.displayName
        }

    }

    override fun onStart() {
        super.onStart()
        mainActivityViewModel.requestUserSignedIn()
    }

    override fun onResume() {
        super.onResume()
        supportActionBar?.show()
    }

    //region Private methods
    private fun selectedDrawerItem(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.nav_my_games_fragment -> {
                initDrawerNavigation(MyGamesFragment(), getString(R.string.my_games_fragment_title))
            }
            R.id.nav_my_list_fragment -> {
                initDrawerNavigation(LikedGamesFragment(),
                    getString(R.string.liked_games_fragment_title))
            }
            R.id.nav_add_games -> {
                initDrawerNavigation(AddGameFragment(),
                    getString(R.string.add_new_games_fragment_title))
            }
            R.id.nav_last_month_releases -> {
                initDrawerNavigation(LastMonthReleasesFragment(ON_LAST_MONTH_RELEASES_CLICK),
                    getString(
                        R.string.last_month_releases_fragment_title
                    ))
            }

            R.id.nav_next_week_releases -> initDrawerNavigation(
                LastMonthReleasesFragment(
                    ON_NEXT_WEEK_RELEASES_CLICK
                ),
                getString(R.string.next_week_releases_fragment_title)
            )

            R.id.nav_weekly_releases -> initDrawerNavigation(
                LastMonthReleasesFragment(
                    ON_WEEKLY_RELEASES_CLICK
                ),
                getString(R.string.weekly_releases_fragment_title)
            )

            R.id.nav_top_250_games -> initDrawerNavigation(
                LastMonthReleasesFragment(
                    ON_TOP_GAMES_CLICK
                ),
                getString(R.string.top_250_games_fragment_title)
            )

            R.id.nav_best_of_the_year -> initDrawerNavigation(
                LastMonthReleasesFragment(
                    ON_BEST_OF_THE_YEAR_CLICK
                ),
                getString(R.string.best_games_of_the_year_fragment_title)
            )

        }
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            selectedDrawerItem(menuItem)
            true
        }
    }

    private fun initDrawerNavigation(fragment: Fragment, fragmentName: String) {
        navigateTo(fragment)
        drawer.closeDrawers()
        topAppBarTitle?.text = fragmentName
    }

    //endregion
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawer.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit()
    }
}
