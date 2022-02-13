package com.raantech.mycups.data.models.notification

import com.google.gson.annotations.SerializedName

data class Notification(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("body")
	val body: String? = null,

	@field:SerializedName("type")
	val type: Int? = null,

	@field:SerializedName("createdDate")
	val createdDate: String? = null
)