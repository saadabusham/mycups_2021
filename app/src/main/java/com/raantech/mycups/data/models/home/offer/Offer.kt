package com.raantech.mycups.data.models.home.offer

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Offer(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("icon")
    val icon: String? = null,

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("description")
    val description: String? = null

) : Serializable
