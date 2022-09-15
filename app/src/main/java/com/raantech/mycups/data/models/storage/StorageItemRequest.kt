package com.raantech.mycups.data.models.storage

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StorageItemRequest(

    @field:SerializedName("item_id")
    val itemId: Int? = null,

    @field:SerializedName("qty")
    val quantity: Int? = null

) : Serializable
