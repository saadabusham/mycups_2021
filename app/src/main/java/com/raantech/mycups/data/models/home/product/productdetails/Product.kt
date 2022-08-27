package com.raantech.mycups.data.models.home.product.productdetails

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.raantech.mycups.data.models.Price
import com.raantech.mycups.data.models.category.DesignCategory
import com.raantech.mycups.data.models.media.Media
import java.io.Serializable

data class Product(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("base_image")
    val base_image: Media? = null,

    @field:SerializedName("price")
    val price: Price? = null,

    @field:SerializedName("is_fast")
    val is_fast: Boolean? = null,

    @field:SerializedName("can_upload_design")
    var can_upload_design: Boolean? = false,

    @field:SerializedName("is_wishlist")
    var isWishlist: Boolean? = false,

    @field:SerializedName("additional_images")
    var additionalImages: List<Media>? = null,

    @field:SerializedName("designs_categories")
    var designsCategories: List<DesignCategory>? = null,

    @field:SerializedName("measurements")
    var measurements: List<Measurement>? = null

) : Serializable