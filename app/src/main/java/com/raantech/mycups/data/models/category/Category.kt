package com.raantech.mycups.data.models.category

import com.google.gson.annotations.SerializedName
import com.raantech.mycups.data.models.home.product.productdetails.Ads
import java.io.Serializable

data class Category(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("icon")
    val icon: String? = null,

    @field:SerializedName("localIcon")
    val localIcon: Int? = null,

    @field:SerializedName("hasChild")
    val hasChild: Boolean? = null,

    @field:SerializedName("ads")
    var ads: List<Ads>? = null

):Serializable
