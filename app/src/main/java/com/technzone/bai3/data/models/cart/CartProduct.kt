package com.technzone.bai3.data.models.cart

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CartProduct(

    @field:SerializedName("country")
    val country: Country? = null,

    @field:SerializedName("price")
    val price: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("icon")
    val icon: String? = null,

    @field:SerializedName("discountPrice")
    val discountPrice: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("quantity")
    var quantity: Int? = 1,

    @field:SerializedName("productSKUId")
    var productSKUId: Int? = null,

    @field:SerializedName("productType")
    val productType: Int? = null

) : Serializable