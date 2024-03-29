package com.raantech.mycups.data.models.storage

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StorageResponse(

    @field:SerializedName("items")
    val storages: List<Storage>? = null
) : Serializable