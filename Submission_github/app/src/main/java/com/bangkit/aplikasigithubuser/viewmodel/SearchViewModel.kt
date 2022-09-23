package com.bangkit.aplikasigithubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.aplikasigithubuser.api.ApiConfig
import com.bangkit.aplikasigithubuser.model.ItemsUser
import com.bangkit.aplikasigithubuser.model.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel: ViewModel() {
    private val _listUsers = MutableLiveData<ArrayList<ItemsUser>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        const val TAG = "MainViewModel"
    }
    fun searchUser(query: String){
        ApiConfig.getApiService().getUser(query)
            .enqueue(object: Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    _isLoading.value = true
                    if (response.isSuccessful){
                        _listUsers.postValue(response.body()?.items as ArrayList<ItemsUser>?)
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.d("Failure", t.message!!)
                }

            })
    }

    fun getSearchUsers(): LiveData<ArrayList<ItemsUser>> {
        return _listUsers
    }
}