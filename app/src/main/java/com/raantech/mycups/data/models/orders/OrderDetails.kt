package com.raantech.mycups.data.models.orders

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OrderDetails(

	@field:SerializedName("shippingAddressCity")
		val shippingAddressCity: String? = null,

	@field:SerializedName("buyerUsername")
		val buyerUsername: String? = null,

	@field:SerializedName("shippingAddressLatitude")
		val shippingAddressLatitude: Int? = null,

	@field:SerializedName("shippingAddressAddressLine1")
		val shippingAddressAddressLine1: String? = null,

	@field:SerializedName("shippingAddressContactName")
		val shippingAddressContactName: String? = null,

	@field:SerializedName("shippingAddressName")
		val shippingAddressName: String? = null,

	@field:SerializedName("shippingAddressPhoneNumber")
		val shippingAddressPhoneNumber: String? = null,

	@field:SerializedName("shippingAddressLongitude")
		val shippingAddressLongitude: Int? = null,

	@field:SerializedName("buyerName")
		val buyerName: String? = null,

	@field:SerializedName("shippingAddressCountry")
		val shippingAddressCountry: ShippingAddressCountry? = null,

//	@field:SerializedName("products")
//		val products: List<CartProduct>? = null,

	@field:SerializedName("shippingAddressZipCode")
		val shippingAddressZipCode: String? = null,

	@field:SerializedName("createdAt")
		val createdAt: String? = null,

	@field:SerializedName("paymentMethod")
		val paymentMethod: Int? = null,

	@field:SerializedName("id")
		val id: Int? = null,

	@field:SerializedName("buyerIcon")
		val buyerIcon: String? = null,

	@field:SerializedName("shippingAddressAddressLine2")
		val shippingAddressAddressLine2: String? = null,

	@field:SerializedName("status")
		val status: Int? = null,

	@field:SerializedName("contactNumber")
		val contactNumber: String? = null,

	@field:SerializedName("subTotal")
		val subTotal: Double? = null,

	@field:SerializedName("totalDiscount")
		val totalDiscount: Double? = null,

	@field:SerializedName("shippingPrice")
		val shippingPrice: Double? = null,

	@field:SerializedName("price")
		val price: Double? = null,

	@field:SerializedName("quantitiy")
		val quantity: Int? = null,
) : Serializable {
    fun getDescription(): String {
        return "$shippingAddressContactName, $shippingAddressPhoneNumber, ${shippingAddressCountry?.name}, $shippingAddressCity, $shippingAddressZipCode, $shippingAddressAddressLine1 ${if (shippingAddressAddressLine2.isNullOrEmpty()) "" else ",$shippingAddressAddressLine2"}"
    }
}