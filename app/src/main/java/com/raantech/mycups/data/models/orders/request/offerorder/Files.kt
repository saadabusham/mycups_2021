package com.raantech.mycups.data.models.orders.request.offerorder

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Files(

    @field:SerializedName("design_file")
    val designFile: Int? = null
) : Serializable