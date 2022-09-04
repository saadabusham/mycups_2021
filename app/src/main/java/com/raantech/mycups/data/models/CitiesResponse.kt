package com.raantech.mycups.data.models

import com.google.gson.annotations.SerializedName
import com.raantech.mycups.data.models.general.City
import java.io.Serializable

data class CitiesResponse(

	@field:SerializedName("cities")
	val cities: List<City>? = null
):Serializable