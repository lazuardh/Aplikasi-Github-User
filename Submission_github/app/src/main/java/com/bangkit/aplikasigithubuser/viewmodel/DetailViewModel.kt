package com.bangkit.aplikasigithubuser.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.bangkit.aplikasigithubuser.api.ApiConfig
import com.bangkit.aplikasigithubuser.data.entity.UserFavorite
import com.bangkit.aplikasigithubuser.data.room.FavoriteDatabase
import com.bangkit.aplikasigithubuser.data.room.FavoriteUserDao
import com.bangkit.aplikasigithubuser.model.DetailResponse
import com.bangkit.aplikasigithubuser.model.ItemsUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val _listDetail = MutableLiveData<DetailResponse>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val btnSuksesFavorite = MutableLiveData<UserFavorite>()
    val btnDeleteFavorite = MutableLiveData<UserFavorite>()

    private var userDao: FavoriteUserDao?
    private var dB : FavoriteDatabase? = FavoriteDatabase.getDatabase(application)

    init {
        userDao = dB?.favoriteUserDao()
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun setDetail(username: String) {
        ApiConfig.getApiService().getUserDetail(username)
            .enqueue(object : Callback<DetailResponse> {
                override fun onResponse(
                    call: Call<DetailResponse>,
                    response: Response<DetailResponse>
                ) {
                    _isLoading.value = true
                    if (response.isSuccessful) {
                        _listDetail.postValue(response.body())
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.d("Failure", t.message!!)
                }

            })
    }

    fun getDetailUsers(): MutableLiveData<DetailResponse> {
        return _listDetail
    }

    //menambah favorite
    fun findFavorite( username: String, id: Int, avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = UserFavorite(
                username,
                id,
                avatarUrl,
            )
            userDao?.addToFavorite(user)
        }
    }

    fun findById(id: Int) = userDao?.findById(id)

    fun removeFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.delete(id)
        }
    }
}