package com.raantech.mycups.data.models.home.product.productdetails

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SocialMedia(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("masterSocialMediaIcon",alternate = ["icon"])
    val icon: String? = null,

    @field:SerializedName("masterSocialMediaTitle" ,alternate = ["name"])
    val name: String? = null,

    @field:SerializedName("lookupSocialMediaLink",alternate = ["data"])
    val data: String? = null,

    @field:SerializedName("lookupSocialMediaAlternativeLink",alternate = ["alternativeLink"])
    val alternativeLink: String? = null,

    @field:SerializedName("lookupSocialMediaTypeID",alternate = ["type"])
    val type: Int? = null
) : Serializable
