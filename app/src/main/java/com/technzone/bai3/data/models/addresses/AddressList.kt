package com.technzone.bai3.data.models.addresses

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import java.io.Serializable

data class AddressList(

	@field:SerializedName("zipCode")
	val zipCode: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("contactName")
	val contactName: String? = null,

	@field:SerializedName("latitude")
	val latitude: Int? = null,

	@field:SerializedName("countryId")
	val countryId: Int? = null,

	@field:SerializedName("isDefault")
	val isDefault: Boolean? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("addressLine1")
	val addressLine1: String? = null,

	@field:SerializedName("addressLine2")
	val addressLine2: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("countryName")
	val countryName: String? = null,

	@field:SerializedName("longitude")
	val longitude: Int? = null,

	@field:Json(name = "countryCode")
	val countryCode: String? = null,
) : Serializable {

    fun getDescription(): String {
        return "$contactName, $countryCode$phoneNumber, $countryName, $city, $zipCode, $addressLine1${if (addressLine2.isNullOrEmpty()) "" else ",$addressLine2"}"
    }
}