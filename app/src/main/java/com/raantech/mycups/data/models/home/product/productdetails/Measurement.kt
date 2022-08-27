package com.raantech.mycups.data.models.home.product.productdetails

import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Measurement(

	@field:SerializedName("diameter")
	val diameter: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("height")
	val height: Int? = null,

	@Transient
	val count: MutableLiveData<Int> = MutableLiveData(0)
):Serializable