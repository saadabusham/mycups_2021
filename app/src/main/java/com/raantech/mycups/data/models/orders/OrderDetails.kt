package com.raantech.mycups.data.models.orders

import com.google.gson.annotations.SerializedName
import com.raantech.mycups.data.enums.OrderStatusEnum
import com.raantech.mycups.data.models.Price
import com.raantech.mycups.data.models.home.product.productdetails.Product
import java.io.Serializable

data class OrderDetails(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("offer_id")
    val offer_id: Int? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("measurements")
    val measurements: List<MeasurementOffer>? = null,

    @field:SerializedName("product")
    val product: Product? = null,

    @field:SerializedName("total")
    val total: Price? = null,

    @field:SerializedName("stock_fees")
    val stockFees: Price? = null,

    @field:SerializedName("sub_total")
    val subTotal: Any? = null,

    @field:SerializedName("vat")
    val vat: Any? = null,

    @field:SerializedName("has_stock")
    val hasStock: Boolean? = null

) : Serializable{
    fun showPay():Boolean{
        return status == OrderStatusEnum.HAS_OFFERS.value
    }
}