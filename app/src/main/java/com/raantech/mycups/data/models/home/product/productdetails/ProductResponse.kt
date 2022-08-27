package com.raantech.mycups.data.models.home.product.productdetails

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductResponse(

    @field:SerializedName("product")
    val product: Product? = null
) : Serializable