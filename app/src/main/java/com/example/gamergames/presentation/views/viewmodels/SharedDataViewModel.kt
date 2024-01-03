package com.example.gamergames.presentation.views.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class SharedDataViewModel : ViewModel() {

    companion object {
        private const val DEFAULT_WEB_CLIENT = "816166894071-dj9lg53a63d1dicptn6i35opa70rkvmq.apps.googleusercontent.com" }

    private val isGameClickedLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val googleSignInClientLiveData: MutableLiveData<GoogleSignInClient> = MutableLiveData()
    private val googleSignInAccountLiveData: MutableLiveData<GoogleSignInAccount> = MutableLiveData()

    fun getGoogleSignInClientLiveData() = googleSignInClientLiveData as LiveData<GoogleSignInClient>
    fun isGameClicked(isClicked: Boolean) {
        if (isClicked) isGameClickedLiveData.postValue(true) else isGameClickedLiveData.postValue(
            false
        )
    }

    fun getGoogleSignInAccountLiveData() =
        googleSignInAccountLiveData as LiveData<GoogleSignInAccount>

    fun setGoogleSignInClient(context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .requestIdToken(DEFAULT_WEB_CLIENT)
            .build()
        val signInClient = GoogleSignIn.getClient(context, gso)
        googleSignInClientLiveData.postValue(signInClient)
    }

    fun setGoogleAccount(account: GoogleSignInAccount) {
        googleSignInAccountLiveData.postValue(account)
    }
}