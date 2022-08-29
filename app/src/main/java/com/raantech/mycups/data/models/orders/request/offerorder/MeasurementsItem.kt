package com.raantech.mycups.data.models.orders.request.offerorder

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MeasurementsItem(

	@field:SerializedName("qty")
	val qty: Int? = null,

	@field:SerializedName("measurement_id")
	val measurementId: Int? = null
): Serializable