package com.raantech.mycups.data.models.home.offer

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AmountPrices(

	@field:SerializedName("shippingPrice")
	val shippingPrice: Double? = 0.0,

	@field:SerializedName("stockPrice")
	var stockPrice: Double? = 0.0,

	@field:SerializedName("taxPrice")
	var taxPrice: Double? = 0.0,

	@field:SerializedName("totalPrice")
	var totalPrice: Double? = 0.0,

	@field:SerializedName("discountPrice")
	val discountPrice: Double? = null,

	@field:SerializedName("subtotalPrice")
	var subtotalPrice: Double? = 0.0
):Serializable