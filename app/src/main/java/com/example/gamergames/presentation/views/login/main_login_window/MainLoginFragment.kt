package com.example.gamergames.presentation.views.login.main_login_window

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.gamergames.R
import com.example.gamergames.presentation.views.interfaces.Navigation
import com.example.gamergames.presentation.views.login.login.LoginFragment
import com.example.gamergames.presentation.views.login.signin.SignInFragment
import com.example.gamergames.presentation.views.main_activity.MainActivity

class MainLoginFragment : Fragment(), Navigation {

    /**Fragment which makes user navigate to login or sign in screen*/
    private var loginButton: Button? = null
    private var signInButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_login_window, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton = view.findViewById(R.id.btn_main_login_window)
        signInButton = view.findViewById(R.id.btn_main_signin_window)

        (requireActivity() as MainActivity).supportActionBar!!.hide()


        loginButton?.setOnClickListener {
            navigateTo(LoginFragment())
        }
        signInButton?.setOnClickListener {
            navigateTo(SignInFragment())
        }
    }
    override fun navigateTo(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.flContent, fragment).commit()
    }
}