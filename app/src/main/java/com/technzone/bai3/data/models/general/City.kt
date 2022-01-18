package com.technzone.bai3.data.models.general

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class City(
    @field:SerializedName("id")
    val id: Int? = null,
    @field:SerializedName("name" , alternate = ["localizedName"])
    val name: String? = null,
    @field:SerializedName("flag")
    val flag: String? = null,
    @field:SerializedName("code")
    val code: String? = null,
    @field:SerializedName("regex")
    val regex: String? = null,
    var selected: Boolean = false
) : Serializable {
    override fun toString(): String {
        return "$code"
    }
    fun getDataString(): String {
        return "$name ($code)"
    }
}
