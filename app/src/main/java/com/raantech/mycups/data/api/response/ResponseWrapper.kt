package com.raantech.mycups.data.api.response

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import java.io.Serializable

data class ResponseWrapper<RETURN_MODEL>(

    @field:SerializedName("errors")
    val errors: List<GeneralError>,
    @field:SerializedName("code")
    val code: Int,
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("body")
    val body: RETURN_MODEL?
):Serializable