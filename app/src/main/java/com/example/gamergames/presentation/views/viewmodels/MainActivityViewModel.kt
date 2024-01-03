package com.example.gamergames.presentation.views.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gamergames.presentation.views.login.FirebaseInstance
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class MainActivityViewModel : ViewModel() {

    private val firebaseInstance = FirebaseInstance.firebaseInstance
    private val isUserSignedInLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val userGoogleProfileImage: MutableLiveData<String> = MutableLiveData()
    private val userGoogleUserName: MutableLiveData<String> = MutableLiveData()
    private val userGoogleUserEmail: MutableLiveData<String> = MutableLiveData()

    fun getUserSignedInStateLiveData() = isUserSignedInLiveData as LiveData<Boolean>
    fun getUserGoogleProfileImage() = userGoogleProfileImage as LiveData<String>
    fun getUserGoogleUserName() = userGoogleUserName as LiveData<String>
    fun getUserGoogleUserEmail() = userGoogleUserEmail as LiveData<String>
    fun requestUserSignedIn() {
        val signed = firebaseInstance.currentUser != null
        isUserSignedInLiveData.postValue(signed)
    }
    fun signOut(){
        firebaseInstance.signOut()
    }

    fun requestGoogleAccountData(context: Context){
        val userGoogleAccount: GoogleSignInAccount = GoogleSignIn.getLastSignedInAccount(context) ?: GoogleSignInAccount.createDefault()
        userGoogleProfileImage.postValue(userGoogleAccount.photoUrl.toString())
        userGoogleUserEmail.postValue(userGoogleAccount.email)
        userGoogleUserName.postValue(userGoogleAccount.displayName)
    }
}