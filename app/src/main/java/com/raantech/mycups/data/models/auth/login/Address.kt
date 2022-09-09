package com.raantech.mycups.data.models.auth.login

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Address(

	@field:SerializedName("address_lng")
	val addressLng: Double? = null,

	@field:SerializedName("address_text")
	val addressText: String? = null,

	@field:SerializedName("address_lat")
	val addressLat: Double? = null
):Serializable