package com.technzone.bai3.data.models.home.product.productdetails

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product(

	@field:SerializedName("createdDate")
	@ColumnInfo(name = "createdDate")
	val createdDate: String? = null,

	@field:SerializedName("discounExpDate")
	@ColumnInfo(name = "discounExpDate")
	val discounExpDate: String? = null,

	@field:SerializedName("price")
	@ColumnInfo(name = "price")
	val price: Double? = null,

	@field:SerializedName("name")
	@ColumnInfo(name = "name")
	val name: String? = null,

	@field:SerializedName("icon")
	@ColumnInfo(name = "icon")
	val icon: String? = null,

	@field:SerializedName("description")
	@ColumnInfo(name = "description")
	val description: String? = null,

	@field:SerializedName("id")
	@PrimaryKey(autoGenerate = false)
	@ColumnInfo(name = "id")
	val id: Int? = null,

	@field:SerializedName("categoryId")
	@ColumnInfo(name = "categoryId")
	val categoryId: Int? = null,

	@field:SerializedName("categoryName")
	@ColumnInfo(name = "categoryName")
	val categoryName: String? = null,

	@field:SerializedName("categoryType")
	@ColumnInfo(name = "categoryType")
	val categoryType: Int? = null,

	@field:SerializedName("shopUsername")
	@ColumnInfo(name = "shopUsername")
	val shopUsername: String? = null,

	@field:SerializedName("favorite")
	@ColumnInfo(name = "favorite")
	var favorite: Boolean = false,

	@field:SerializedName("discounStartDate")
	@ColumnInfo(name = "discounStartDate")
	val discounStartDate: String? = null,

	@field:SerializedName("productType")
	@ColumnInfo(name = "productType")
	val productType: Int? = null,

	@field:SerializedName("soldOut")
	@ColumnInfo(name = "soldOut")
	val soldOut: Boolean? = null

):Serializable{
	fun bedCount():Int{
		return 2
	}
	fun bathCount():Int{
		return 3
	}
}