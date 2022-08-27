package com.raantech.mycups.data.models.home.homedata

import com.google.gson.annotations.SerializedName
import com.raantech.mycups.data.models.category.DesignCategory
import com.raantech.mycups.data.models.home.offer.Offer
import com.raantech.mycups.data.models.home.product.productdetails.Product
import java.io.Serializable

data class HomeResponse(

    @field:SerializedName("offers")
    val offers: List<Offer>? = null,

    @field:SerializedName("latest_designs")
    val latestDesigns: List<DesignCategory>? = null,

    @field:SerializedName("categories")
    val categories: List<CategoriesItem>? = null,

    @field:SerializedName("latest_products")
    val latestProducts: List<Product>? = null
) : Serializable