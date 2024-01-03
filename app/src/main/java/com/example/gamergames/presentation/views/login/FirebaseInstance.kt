package com.example.gamergames.presentation.views.login

import com.google.firebase.auth.FirebaseAuth

object FirebaseInstance {
    /**Singleton design pattern to get the same firebase instance every time we call it*/
    val firebaseInstance: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
}