package com.bangkit.aplikasigithubuser.api

import com.bangkit.aplikasigithubuser.User
import com.bangkit.aplikasigithubuser.model.*
import com.bangkit.aplikasigithubuser.viewmodel.FollowersResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUser(
        @Query("q") query: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowersUsers(
        @Path("username") username: String
    ): Call<List<FollowersResponseItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowingUsers(
        @Path("username") username: String
    ): Call<List<FollowingResponseItem>>
}