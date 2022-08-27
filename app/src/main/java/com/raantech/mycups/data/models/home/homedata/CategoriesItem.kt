package com.raantech.mycups.data.models.home.homedata

import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.SerializedName
import com.raantech.mycups.data.models.media.Media
import java.io.Serializable

data class CategoriesItem(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("logo")
    val logo: String? = null,

    @field:SerializedName("has_sub")
    val hasSub: Boolean? = null,

    @Transient
    var isSelected: MutableLiveData<Boolean> = MutableLiveData(false)

) : Serializable