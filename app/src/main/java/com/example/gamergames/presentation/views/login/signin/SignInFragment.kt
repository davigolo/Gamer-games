package com.example.gamergames.presentation.views.login.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gamergames.R
import com.example.gamergames.presentation.views.interfaces.Navigation
import com.example.gamergames.presentation.views.login.login.LoginFragment
import com.example.gamergames.presentation.views.login.main_login_window.MainLoginFragment
import com.google.android.material.textfield.TextInputEditText

class SignInFragment : Fragment(), Navigation {

    /**Fragment where user can SignIn to the app*/
    //region Private values
    private val signInFragmentViewModel by lazy { ViewModelProvider(this)[SignInFragmentViewModel::class.java] }
    private var signInButton: Button? = null
    private var passField: TextInputEditText? = null
    private var emailField: TextInputEditText? = null
    private var isAccCreated: Boolean? = null
    private var toolbar: Toolbar? = null
    var fragment: Fragment = MainLoginFragment()
    //endregion
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signin, container, false)
    }

    /**The sign in button will only be enabled when input fields are filled*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.toolbar)
        signInButton = view.findViewById(R.id.btn_signin)
        passField = view.findViewById(R.id.singing_password_field)
        emailField = view.findViewById(R.id.login_email_field)

        toolbar?.visibility = View.INVISIBLE

        emailField?.doOnTextChanged { _, _, _, _ ->
            checkSingInButtonEnabled()
        }
        passField?.doOnTextChanged { _, _, _, _ ->
            checkSingInButtonEnabled()
        }

        signInButton?.setOnClickListener {
            signInFragmentViewModel.createNewUser(
                emailField?.text.toString(),
                passField?.text.toString()
            )
            signInFragmentViewModel.isUserCreatedSuccessful.observe(viewLifecycleOwner) {
                isAccCreated = it
            }
            if (isAccCreated == true) {
                navigateTo(LoginFragment())
                fragment = SignInFragment()
            } else Toast.makeText(context, "Error while creating account", Toast.LENGTH_SHORT)
                .show()
        }
        onBackPressed()
    }

    //region Private methods
    private fun onBackPressed(){
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navigateTo(fragment)
                }
            })
    }
    private fun checkSingInButtonEnabled() {
        signInButton?.isEnabled = (emailField?.text?.isNotEmpty() ?: false) &&
                (passField?.text?.isNotEmpty() ?: false) &&
                (emailField?.text?.contains("@") ?: false)
    }
    //endregion

    override fun navigateTo(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.flContent, fragment).commit()
    }
}