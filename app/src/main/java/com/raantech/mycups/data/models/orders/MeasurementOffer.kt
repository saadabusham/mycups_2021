package com.raantech.mycups.data.models.orders

import com.google.gson.annotations.SerializedName
import com.raantech.mycups.data.models.Price
import java.io.Serializable

data class MeasurementOffer(

	@field:SerializedName("offer")
	val offer: Price? = null,

	@field:SerializedName("name")
	val name: String? = null
):Serializable