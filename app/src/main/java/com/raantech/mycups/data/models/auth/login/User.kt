package com.raantech.mycups.data.models.auth.login

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(

	@field:SerializedName("has_stock")
	val hasStock: Boolean? = null,

	@field:SerializedName("address")
	val address: Address? = null,

	@field:SerializedName("last_login")
	val lastLogin: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("brand_name")
	val brandName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("picture")
	val picture: String? = null
):Serializable