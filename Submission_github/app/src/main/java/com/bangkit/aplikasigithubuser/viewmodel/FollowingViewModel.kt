package com.bangkit.aplikasigithubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.aplikasigithubuser.api.ApiConfig
import com.bangkit.aplikasigithubuser.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {
    private val _listFollowing = MutableLiveData<ArrayList<FollowingResponseItem>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setFollowing(username: String){
        ApiConfig.getApiService().getFollowingUsers(username)
            .enqueue(object: Callback<List<FollowingResponseItem>> {
                override fun onResponse(
                    call: Call<List<FollowingResponseItem>>,
                    response: Response<List<FollowingResponseItem>>
                ) {
                    _isLoading.value = true
                    if (response.isSuccessful){
                        _listFollowing.postValue(response.body() as ArrayList<FollowingResponseItem>?)
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
                override fun onFailure(call: Call<List<FollowingResponseItem>>, t: Throwable) {
                    _isLoading.value = false
                    Log.d("Failure", t.message!!)
                }

            })
    }

    fun getFollowing(): LiveData<ArrayList<FollowingResponseItem>> {
        return _listFollowing
    }

    companion object {
        const val TAG = "MainViewModel"
    }
}
