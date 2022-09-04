package com.raantech.mycups.data.models.home.product.productdetails

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.raantech.mycups.data.db.ApplicationDB
import com.raantech.mycups.data.models.Price
import com.raantech.mycups.data.models.category.DesignCategory
import com.raantech.mycups.data.models.media.Media
import java.io.Serializable

@Entity(tableName = ApplicationDB.TABLE_CART)
data class Product(

    @PrimaryKey(autoGenerate = false)
    @field:SerializedName("id")
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @field:SerializedName("qty")
    @ColumnInfo(name = "qty")
    var qty: Int? = null,

    @field:SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String? = null,

    @field:SerializedName("description")
    @ColumnInfo(name = "description")
    val description: String? = null,

    @field:SerializedName("base_image")
    @ColumnInfo(name = "base_image")
    val base_image: Media? = null,

    @field:SerializedName("price")
    @ColumnInfo(name = "price")
    val price: Price? = null,

    @field:SerializedName("is_fast")
    @ColumnInfo(name = "is_fast")
    val is_fast: Boolean? = null,

    @field:SerializedName("can_upload_design")
    @ColumnInfo(name = "can_upload_design")
    var can_upload_design: Boolean? = false,

    @field:SerializedName("is_wishlist")
    @ColumnInfo(name = "is_wishlist")
    var isWishlist: Boolean? = false,

    @field:SerializedName("additional_images")
    @ColumnInfo(name = "additional_images")
    var additionalImages: List<Media>? = null,

    @field:SerializedName("designs_categories")
    @ColumnInfo(name = "designs_categories")
    var designsCategories: List<DesignCategory>? = null,

    @field:SerializedName("measurements")
    @ColumnInfo(name = "measurements")
    var measurements: List<Measurement>? = null

) : Serializable