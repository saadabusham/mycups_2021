package com.technzone.bai3.data.models.home.product.productdetails

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Shop(

    @field:SerializedName("rateCount")
    val rateCount: Int? = null,

    @field:SerializedName("rate")
    val rate: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("icon")
    val icon: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("title")
    val title: Int? = null,

    @field:SerializedName("userId")
    val userId: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("latitude")
    val latitude: Double? = null,

    @field:SerializedName("longitude")
    val longitude: Double? = null,

    @field:SerializedName("socialMedia")
    val socialMedia: List<SocialMedia>? = null
):Serializable