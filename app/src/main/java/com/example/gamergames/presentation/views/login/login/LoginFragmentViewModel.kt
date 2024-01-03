package com.example.gamergames.presentation.views.login.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gamergames.presentation.views.login.FirebaseInstance
import com.example.gamergames.presentation.views.main_activity.MainActivity
import com.google.firebase.auth.GoogleAuthProvider

class LoginFragmentViewModel : ViewModel() {

    private val firebaseInstance = FirebaseInstance.firebaseInstance
    val isUserLoggedIn: MutableLiveData<Boolean> = MutableLiveData()
    private val signedGoogleUser: MutableLiveData<Boolean> = MutableLiveData()

    fun getGoogleSignedUserState() = signedGoogleUser as LiveData<Boolean>

    fun logInUser(email: String, password: String): LiveData<Boolean> {
        firebaseInstance.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(MainActivity()) { task ->
                if (task.isSuccessful) {
                    isUserLoggedIn.postValue(true)
                } else {
                    isUserLoggedIn.postValue(false)
                }
            }
        return isUserLoggedIn
    }

    fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseInstance.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) signedGoogleUser.postValue(true) else signedGoogleUser.postValue(
                false
            )
        }
    }

}