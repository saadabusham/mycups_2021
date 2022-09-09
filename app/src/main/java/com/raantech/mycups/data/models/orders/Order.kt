package com.raantech.mycups.data.models.orders

import com.google.gson.annotations.SerializedName
import com.raantech.mycups.data.models.Price
import java.io.Serializable

data class Order(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("order_number")
	val orderNumber: String? = null,

	@field:SerializedName("status_text")
	val statusText: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("total")
	val total: Price? = null,

	@field:SerializedName("order_date")
	val orderDate: String? = null,

	@field:SerializedName("is_offer")
	val isOffer: Boolean? = null
):Serializable