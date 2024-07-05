package com.rivzdev.githubuserapp.data.source.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class UserResponse(

	@field:SerializedName("items")
	val items: List<ItemsItem>
)

@Parcelize
data class ItemsItem(

	@field:SerializedName("avatar_url")
	val avatarUrl: String?,

	@field:SerializedName("login")
	val login: String?,

	@field:SerializedName("public_repos")
	val publicRepository: Int?,

	@field:SerializedName("followers")
	val followers: Int?,

	@field:SerializedName("following")
	val following: Int?,

	@field:SerializedName("name")
	val name: String?
): Parcelable
