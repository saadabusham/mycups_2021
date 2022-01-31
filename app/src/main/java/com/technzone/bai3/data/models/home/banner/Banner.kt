package com.technzone.bai3.data.models.home.banner

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Banner(

	@field:SerializedName("arabicName")
	val arabicName: String? = null,

	@field:SerializedName("englishName")
	val englishName: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("color")
	val color: String? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("type")
	val type: Int? = null,

	@field:SerializedName("value")
	val value: String? = null
):Serializable