package com.raantech.mycups.data.models.orders

import com.google.gson.annotations.SerializedName
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.data.models.media.Media
import java.io.Serializable

data class OrderDetails(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("icon")
	val icon: Media? = null,

	@field:SerializedName("name")
	var name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("products")
	val products: List<Product>? = null

) : Serializable