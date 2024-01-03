package com.example.gamergames.presentation.views.login.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gamergames.presentation.views.login.FirebaseInstance

class SignInFragmentViewModel : ViewModel() {

    private val firebaseInstance = FirebaseInstance.firebaseInstance
    val isUserCreatedSuccessful: MutableLiveData<Boolean> = MutableLiveData()

    fun createNewUser(email: String, password: String): LiveData<Boolean> {
        firebaseInstance.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    isUserCreatedSuccessful.postValue(true)
                } else {
                    isUserCreatedSuccessful.postValue(false)
                }
            }
        return isUserCreatedSuccessful
    }
}