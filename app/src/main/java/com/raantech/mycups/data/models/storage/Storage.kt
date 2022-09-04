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

    @field:SerializedName("measurement")
    val measurement: Measurement? = null,

    @field:SerializedName("quantitiy")
    val quantity: Int? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("category")
    val category: CategoriesItem? = null,

    @Transient
    val count: MutableLiveData<Int> = MutableLiveData(0)
) : Serializable