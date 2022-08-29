package com.raantech.mycups.data.models.orders.request.offerorder

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OfferOrderRequest(

    @field:SerializedName("order_type")
    val orderType: String? = null,

    @field:SerializedName("has_stock")
	val hasStock: Boolean? = null,

    @field:SerializedName("design_id")
    var designId: Int? = null,

    @field:SerializedName("product")
	val product: OfferOrderProduct? = null,

    @field:SerializedName("files")
	val files: Files? = null

):Serializable