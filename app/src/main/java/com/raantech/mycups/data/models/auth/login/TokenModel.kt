package com.raantech.mycups.data.models.auth.login

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import java.io.Serializable

data class TokenModel(

    @field:SerializedName("token")
    val token: String? = null
): Serializable