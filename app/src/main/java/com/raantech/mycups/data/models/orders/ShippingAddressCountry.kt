package com.raantech.mycups.data.models.orders

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ShippingAddressCountry(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
):Serializable