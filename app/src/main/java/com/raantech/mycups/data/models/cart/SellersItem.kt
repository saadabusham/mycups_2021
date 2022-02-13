package com.raantech.mycups.data.models.cart

import com.google.gson.annotations.SerializedName

data class SellersItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: Int? = null,

	@field:SerializedName("countryId")
	val countryId: Int? = null,

	@field:SerializedName("domisticShippingPrice")
	val domisticShippingPrice: Int? = null,

	@field:SerializedName("worldwideShippingPrice")
	val worldwideShippingPrice: Int? = null,

	@field:SerializedName("products")
	val products: List<CartProduct>? = null
)