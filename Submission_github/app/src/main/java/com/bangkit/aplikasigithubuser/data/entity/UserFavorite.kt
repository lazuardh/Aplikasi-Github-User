package com.bangkit.aplikasigithubuser.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user_favorite")
data class UserFavorite (
    @ColumnInfo(name = "login")
    val login: String,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "avatar_url")
    val avatar_url: String
    ) : Parcelable