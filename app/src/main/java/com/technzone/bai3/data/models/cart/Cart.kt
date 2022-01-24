package com.technzone.bai3.data.models.cart

import com.google.gson.annotations.SerializedName

data class Cart(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("productsCount")
	val productsCount: Int? = null,

	@field:SerializedName("sellers")
	val sellers: List<SellersItem?>? = null
)