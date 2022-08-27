package com.raantech.mycups.data.models.category

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Category(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("logo")
    val logo: String? = null,

    @field:SerializedName("localIcon")
    val localIcon: Int? = null,

    @field:SerializedName("has_sub")
    val has_sub: Boolean? = null,

    @field:SerializedName("items")
    var items: List<Any>? = null

):Serializable
