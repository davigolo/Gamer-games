package com.example.gamergames.presentation.views.login.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.gamergames.R
import com.example.gamergames.presentation.views.fragments.MyGamesFragment
import com.example.gamergames.presentation.views.login.FirebaseInstance
import com.example.gamergames.presentation.views.login.main_login_window.MainLoginFragment
import com.example.gamergames.presentation.views.main_activity.MainActivity
import com.example.gamergames.presentation.views.interfaces.Navigation
import com.example.gamergames.presentation.views.viewmodels.SharedDataViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : Fragment(), Navigation {

    /**Fragment which allows user to log in to the app*/
    //region Private values
    private val loginFragmentViewModel by lazy { ViewModelProvider(this)[LoginFragmentViewModel::class.java] }
    private val sharedDataViewModel: SharedDataViewModel by activityViewModels()
    private var loginButton: Button? = null
    private val googleLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    loginFragmentViewModel.firebaseAuthWithGoogle(account.idToken!!)
                    loginFragmentViewModel.getGoogleSignedUserState()
                        .observe(viewLifecycleOwner) { isAuthSuccessful ->
                            if (isAuthSuccessful) {
                                navigateTo(MyGamesFragment())
                                sharedDataViewModel.setGoogleAccount(account)
                            } else {
                                Toast.makeText(
                                    context,
                                    getString(R.string.error_authenticating_with_google),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } catch (e: Exception) {
                    Log.e("GoogleSignIn", getString(R.string.error_on_googlesigninaccount_task))
                }
            }
        }
    private var email: TextInputEditText? = null
    private var pass: TextInputEditText? = null
    private var googleButton: ImageButton? = null
    private var toolbar: Toolbar? = null
    //endregion
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.toolbar)
        email = view.findViewById(R.id.login_username_field)
        pass = view.findViewById(R.id.login_password_field)
        loginButton = view.findViewById(R.id.btn_login)
        googleButton = view.findViewById(R.id.btn_google_login)

        toolbar?.visibility = View.INVISIBLE

        activity?.let { currentActivity ->
            sharedDataViewModel.setGoogleSignInClient(currentActivity)
            sharedDataViewModel.getGoogleSignInClientLiveData()
                .observe(viewLifecycleOwner) { signInClient ->
                    googleButton?.setOnClickListener {
                        googleLauncher.launch(signInClient.signInIntent)
                    }
                }
        }

        loginButton?.setOnClickListener {
            loginFragmentViewModel.logInUser(
                email?.text.toString(),
                pass?.text.toString()
            )
            loginFragmentViewModel.isUserLoggedIn.observe(viewLifecycleOwner) { isUserLoggedIn ->
                if (isUserLoggedIn == true) {
                    val mainActivity = Intent(activity, MainActivity()::class.java)
                    startActivity(mainActivity)
                    Log.i(
                        "Logged user",
                        getString(R.string.user_signed_in) + FirebaseInstance.firebaseInstance.currentUser?.email
                    )
                } else Toast.makeText(context,
                    getString(R.string.error_while_logging_in), Toast.LENGTH_SHORT)
                    .show()
            }

        }
        onBackPressed()
    }
    private fun onBackPressed(){
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val fragment: Fragment = MainLoginFragment()
                    navigateTo(fragment)
                }
            })
    }
    override fun navigateTo(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.flContent, fragment).commit()
    }
}