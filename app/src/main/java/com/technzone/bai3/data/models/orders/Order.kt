package com.technzone.bai3.data.models.orders

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Order(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("paymentMethod")
	val paymentMethod: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("quantitiy")
	val quantity: Int? = null,

	@field:SerializedName("contactNumber")
	val contactNumber: String? = null
):Serializable