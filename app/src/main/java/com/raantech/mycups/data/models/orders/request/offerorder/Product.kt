package com.raantech.mycups.data.models.orders.request.offerorder

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product(

	@field:SerializedName("product_id")
	val productId: Int? = null,

	@field:SerializedName("measurements")
	val measurements: List<MeasurementsItem?>? = null
): Serializable