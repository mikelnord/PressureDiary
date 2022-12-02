package com.example.pressurediary.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class PressDat(
    var day: Int? = null,
    var month: Int? = null,
    var high: String? = null,
    var low: String? = null,
    var puls: String? = null
)