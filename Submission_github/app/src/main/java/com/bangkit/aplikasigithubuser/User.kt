package com.bangkit.aplikasigithubuser

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var username: String,
    var name: String,
    var location: String,
    var company: String,
    var repository: String,
    var followers: String,
    var following: String,
    var avatar:Int,
): Parcelable
