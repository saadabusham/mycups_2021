package com.raantech.mycups.data.models.orders.request.purchaseorder

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PurchaseOrderRequest(

    @field:SerializedName("order_type")
    val orderType: String? = null,

	@field:SerializedName("has_stock")
	val hasStock: Boolean? = null,

	@field:SerializedName("design_id")
	var designId: Int? = null,

    @field:SerializedName("payment_method")
    val paymentMethod: String? = null,

    @field:SerializedName("products")
    val products: List<PurchaseOrderProducts?>? = null
) : Serializable