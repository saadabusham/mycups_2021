package com.raantech.mycups.data.models.orders.request.purchaseorder

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PurchaseOrderProducts(

	@field:SerializedName("product_id")
	val productId: Int? = null,

	@field:SerializedName("qty")
	val qty: Int? = null
): Serializable