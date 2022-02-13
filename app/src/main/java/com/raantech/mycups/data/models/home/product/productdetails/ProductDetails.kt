package com.raantech.mycups.data.models.home.product.productdetails

import com.google.gson.annotations.SerializedName
import com.raantech.mycups.data.models.general.GeneralLookup
import java.io.Serializable

data class ProductDetails(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("price")
    val price: Double? = null,

    @field:SerializedName("discountPrice")
    val discountPrice: Double? = null,

    @field:SerializedName("discounExpDate")
    val discounExpDate: String? = null,

    @field:SerializedName("createdDate")
    val createdDate: String? = null,

    @field:SerializedName("youtubeUrl")
    val youtubeUrl: String? = null,

    @field:SerializedName("icon")
    val icon: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("soldOut")
    var soldOut: Boolean? = null,

    @field:SerializedName("likes")
    val likes: Int? = null,

    @field:SerializedName("category")
    val category: GeneralLookup? = null,

    @field:SerializedName("images")
    val images: List<String>? = null,

    @field:SerializedName("tags")
    val tags: List<GeneralLookup>? = null,

    @field:SerializedName("productSpecifications")
    val productSpecifications: List<GeneralLookup>? = null,

    @field:SerializedName("shop")
    val shop: Shop? = null,

    var favorite: Boolean = false,

//    Useless
    @field:SerializedName("country")
    val country: GeneralLookup? = null,

    @field:SerializedName("color")
    val color: GeneralLookup? = null,

    @field:SerializedName("gender")
    val gender: GeneralLookup? = null,

    @field:SerializedName("dressCode")
    val dressCode: GeneralLookup? = null,

    @field:SerializedName("condition")
    val condition: GeneralLookup? = null,

    @field:SerializedName("size")
    val size: GeneralLookup? = null,

    @field:SerializedName("style")
    val style: GeneralLookup? = null,

    @field:SerializedName("brand")
    val brand: GeneralLookup? = null,

    @field:SerializedName("productType")
    val productType: Int? = null
) : Serializable {
    fun getShortSpecification(): String {
        return "(Blue, 64 GB)"
    }

    fun isHasYoutubeVideo(): Boolean {
        if (youtubeUrl.isNullOrEmpty() || !youtubeUrl.contains("v="))
            return false
        return true
    }
}