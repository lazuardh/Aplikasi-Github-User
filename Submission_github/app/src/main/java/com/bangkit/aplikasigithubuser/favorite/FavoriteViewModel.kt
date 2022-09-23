package com.bangkit.aplikasigithubuser.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.bangkit.aplikasigithubuser.data.entity.UserFavorite
import com.bangkit.aplikasigithubuser.data.room.FavoriteDatabase
import com.bangkit.aplikasigithubuser.data.room.FavoriteUserDao

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private var userDao: FavoriteUserDao?
    private var dB : FavoriteDatabase? = FavoriteDatabase.getDatabase(application)

    init {
        userDao = dB?.favoriteUserDao()
    }

    fun getFavoriteUser() : LiveData<List<UserFavorite>>? {
        return userDao?.getAllFavorite()
    }
}