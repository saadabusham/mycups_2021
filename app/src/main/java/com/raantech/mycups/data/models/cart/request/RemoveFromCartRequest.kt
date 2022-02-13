package com.raantech.mycups.data.models.cart.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RemoveFromCartRequest(
	@field:SerializedName("productId")
	val productId: Int? = null,

	@field:SerializedName("productSKUId")
	val productSKUId: Int? = null,

    @field:SerializedName("forceDelete")
    val forceDelete: Boolean? = null

) : Serializable