package com.raantech.mycups.data.models.wishlist

import com.google.gson.annotations.SerializedName
import com.raantech.mycups.data.models.home.product.productdetails.Product
import java.io.Serializable

data class WishList(
    @field:SerializedName("products")
    val products: List<Product>? = null,
) : Serializable