package com.bangkit.aplikasigithubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.aplikasigithubuser.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModels : ViewModel() {
    private val _listFollowers = MutableLiveData<ArrayList<FollowersResponseItem>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setFollowers(username: String){
        ApiConfig.getApiService().getFollowersUsers(username)
            .enqueue(object: Callback<List<FollowersResponseItem>> {
                override fun onResponse(
                    call: Call<List<FollowersResponseItem>>,
                    response: Response<List<FollowersResponseItem>>
                ) {
                    _isLoading.value = true
                    if (response.isSuccessful){
                        _listFollowers.postValue(response.body() as ArrayList<FollowersResponseItem>?)
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
                override fun onFailure(call: Call<List<FollowersResponseItem>>, t: Throwable) {
                    _isLoading.value = false
                    Log.d("Failure", t.message!!)
                }
            })
    }

    fun getFollowing(): LiveData<ArrayList<FollowersResponseItem>> {
        return _listFollowers
    }

    companion object {
        const val TAG = "followersViewModel"
    }
}