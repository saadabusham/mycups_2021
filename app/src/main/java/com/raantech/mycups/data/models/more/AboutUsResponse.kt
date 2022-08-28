package com.raantech.mycups.data.models.more

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AboutUsResponse(
	@field:SerializedName("about_us")
	val about_us: String? = null,
	@field:SerializedName("app_email")
	val app_email: String? = null,
	@field:SerializedName("app_phone")
	val app_phone: String? = null
): Serializable