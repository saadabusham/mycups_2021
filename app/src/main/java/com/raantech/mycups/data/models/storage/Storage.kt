package com.raantech.mycups.data.models.storage

import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.SerializedName
import com.raantech.mycups.data.models.Price
import com.raantech.mycups.data.models.home.homedata.CategoriesItem
import com.raantech.mycups.data.models.home.product.productdetails.Measurement
import java.io.Serializable

data class Storage(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("measurement_name")
    val measurementName: String? = null,

    @field:SerializedName("qty")
    val quantity: Int? = null,

    @field:SerializedName("product_name")
    val productName: String? = null,

    @Transient
    val count: MutableLiveData<Int> = MutableLiveData(0)
) : Serializable
