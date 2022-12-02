package com.example.pressurediary.ui

import androidx.lifecycle.ViewModel
import com.example.pressurediary.model.PressDat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate

class HomeViewModel : ViewModel() {
    var isSigningIn: Boolean = false
    private val firestore = Firebase.firestore
    val pressRef = firestore.collection("pressData")

//    init {
//        pressRef.add(
//            PressDat(30, 11, "120", "80", "75")
//        )
//        pressRef.add(PressDat(30, 11, "130", "90", "80"))
//        pressRef.add(PressDat(29, 12, "130", "90", "80"))
//
//    }

    fun addPressDat(pressDat: PressDat) {
        pressRef.add(pressDat)
    }

    fun getMonth(): Int {
        val date = LocalDate.now()
        return date.monthValue
    }

    fun getDay(): Int {
        val date = LocalDate.now()
        return date.dayOfMonth
    }
}