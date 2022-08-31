package com.raantech.mycups.data.models.orders

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OrdersResponse(

    @field:SerializedName("orders")
    val orders: List<Order>? = null
) : Serializable