package com.bangkit.aplikasigithubuser.model

import com.google.gson.annotations.SerializedName

data class DetailResponse(
	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("company")
	val company: String,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("public_repos")
	val public_repos: Int,
)
