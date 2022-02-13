package com.raantech.mycups.data.models.cart

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CheckoutResponse(

		@field:SerializedName("id")
		val id: Int? = null,

		@field:SerializedName("estimatedDelvieryTime")
		val estimatedDelvieryTime: String? = null

) : Serializable