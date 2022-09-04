package com.raantech.mycups.data.models.auth.login

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserDetailsResponseModel(
    @field:SerializedName("access_token")
    val accessToken: String? = null,

    @field:SerializedName("user")
    var user: User? = null
) : Serializable