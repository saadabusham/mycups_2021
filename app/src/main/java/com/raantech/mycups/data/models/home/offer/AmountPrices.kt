package com.raantech.mycups.data.models.home.offer

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AmountPrices(

	@field:SerializedName("shippingPrice")
	val shippingPrice: Double? = 0.0,

	@field:SerializedName("taxPrice")
	val taxPrice: Double? = 0.0,

	@field:SerializedName("totalPrice")
	val totalPrice: Double? = 0.0,

	@field:SerializedName("discountPrice")
	val discountPrice: Double? = null,

	@field:SerializedName("subtotalPrice")
	val subtotalPrice: Double? = 0.0
):Serializable