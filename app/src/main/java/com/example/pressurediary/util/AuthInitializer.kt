package com.example.pressurediary.util

import android.content.Context
import androidx.startup.Initializer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.example.pressurediary.BuildConfig
import com.google.firebase.ktx.Firebase

class AuthInitializer : Initializer<FirebaseAuth> {
    private val AUTH_EMULATOR_HOST = "10.0.2.2"
    private val AUTH_EMULATOR_PORT = 9099

    override fun create(context: Context): FirebaseAuth {
        val firebaseAuth = Firebase.auth
        if (BuildConfig.DEBUG) {
            firebaseAuth.useEmulator(AUTH_EMULATOR_HOST, AUTH_EMULATOR_PORT)
        }
        return firebaseAuth
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}