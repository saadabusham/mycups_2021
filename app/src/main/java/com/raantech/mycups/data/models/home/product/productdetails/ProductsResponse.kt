package com.raantech.mycups.data.models.home.product.productdetails

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductsResponse(

    @field:SerializedName("products")
    val products: List<Product>? = null
) : Serializable