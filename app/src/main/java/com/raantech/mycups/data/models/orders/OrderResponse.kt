package com.raantech.mycups.data.models.orders

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OrderResponse(

    @field:SerializedName("order")
    val order: OrderDetails? = null
) : Serializable