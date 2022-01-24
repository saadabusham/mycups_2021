package com.technzone.bai3.data.models.promocode

import com.google.gson.annotations.SerializedName

data class PromoCode(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("endDate")
	val endDate: String? = null,

	@field:SerializedName("discount")
	val discount: Int? = null,

	@field:SerializedName("discountType")
	val discountType: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("allowMultipleUsage")
	val allowMultipleUsage: Boolean? = null,

	@field:SerializedName("startDate")
	val startDate: String? = null,

	@field:SerializedName("discountLimit")
	val discountLimit: Int? = null
)