package com.raantech.mycups.data.models.storage

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StorageRequest(

    @field:SerializedName("items")
    val storages: List<StorageItemRequest>? = null
) : Serializable